package ir.msob.jima.crud.api.rsocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import ir.msob.jima.core.beans.properties.JimaProperties;
import ir.msob.jima.core.ral.mongo.test.configuration.MongoContainerConfiguration;
import ir.msob.jima.security.ral.keycloak.test.KeycloakContainerConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.testcontainers.containers.MongoDBContainer;

@TestConfiguration
public class TestBeanConfiguration {

    /**
     * Registers dynamic properties for the Kafka container.
     *
     * @param mongoDBContainer The Mongo container instance.
     * @return The DynamicPropertyRegistrar bean.
     */
    @Bean
    public DynamicPropertyRegistrar dynamicPropertyRegistrar(MongoDBContainer mongoDBContainer, KeycloakContainer keycloakContainer, JimaProperties jimaProperties) {
        return registry -> {
            MongoContainerConfiguration.registry(registry, mongoDBContainer);
            KeycloakContainerConfiguration.registry(registry, keycloakContainer, jimaProperties);
        };
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
