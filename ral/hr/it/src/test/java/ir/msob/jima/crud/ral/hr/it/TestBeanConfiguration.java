package ir.msob.jima.crud.ral.hr.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.spi.ConnectionFactory;
import ir.msob.jima.core.beans.properties.JimaProperties;
import ir.msob.jima.core.commons.element.BaseElement;
import ir.msob.jima.core.ral.postgresql.test.PostgreSQLContainerConfiguration;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.testcontainers.postgresql.PostgreSQLContainer;
import reactor.core.publisher.Mono;

import java.util.UUID;

@TestConfiguration
public class TestBeanConfiguration {

    @Bean
    public DynamicPropertyRegistrar dynamicPropertyRegistrar(PostgreSQLContainer postgreSQLContainer, JimaProperties jimaProperties) {
        return registry -> {
            PostgreSQLContainerConfiguration.registry(registry, postgreSQLContainer);
        };
    }


    @Bean
    public R2dbcEntityTemplate r2dbcEntityTemplate(ConnectionFactory connectionFactory) {
        return new R2dbcEntityTemplate(connectionFactory);
    }

    @Bean
    public ApplicationRunner schemaInitializer(DatabaseClient databaseClient) {
        return args -> {
            databaseClient.sql(
                    "CREATE TABLE IF NOT EXISTS test_domain (" +
                            "id TEXT PRIMARY KEY, " +
                            "domain_field TEXT NOT NULL" +
                            ");"
            ).then().block(); // acceptable in test initialization
        };
    }

    @Bean
    public BeforeConvertCallback<BaseElement<String>> testDomainBeforeConvert() {
        return (entity, table) -> {
            if (entity.getId() == null) {
                entity.setId(UUID.randomUUID().toString());
            }
            return Mono.just(entity);
        };
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
