package ir.msob.jima.crud.ral.jpa.it;

import ir.msob.jima.core.beans.properties.JimaProperties;
import ir.msob.jima.core.ral.postgresql.test.PostgreSQLContainerConfiguration;
import jakarta.persistence.EntityManager;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
public class TestBeanConfiguration {

    @Bean
    public DynamicPropertyRegistrar dynamicPropertyRegistrar(PostgreSQLContainer<?> postgreSQLContainer, JimaProperties jimaProperties) {
        return registry -> {
            PostgreSQLContainerConfiguration.registry(registry, postgreSQLContainer);
        };
    }

    @Bean
    public ApplicationRunner schemaInitializer(EntityManager entityManager, PlatformTransactionManager transactionManager) {
        return args -> {
            TransactionTemplate txTemplate = new TransactionTemplate(transactionManager);
            txTemplate.execute(status -> {
                entityManager.createNativeQuery(
                        "CREATE TABLE IF NOT EXISTS test_domain (" +
                                "id TEXT PRIMARY KEY, " +
                                "domain_field TEXT NOT NULL" +
                                ")"
                ).executeUpdate();
                return null;
            });
        };
    }


}
