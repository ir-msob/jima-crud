package ir.msob.jima.graphql.restful.reactive.test;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import ir.msob.jima.platform.api.properties.TestContainerProperties;
import ir.msob.jima.platform.mongo.testing.configuration.MongoContainerConfiguration;
import ir.msob.jima.security.keycloak.testing.configuration.KeycloakContainerConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.testcontainers.mongodb.MongoDBContainer;

@TestConfiguration
public class TestBeanConfiguration {

    /**
     * Registers dynamic properties for the Kafka container.
     *
     * @param mongoDBContainer The Mongo container instance.
     * @return The DynamicPropertyRegistrar bean.
     */
    @Bean
    public DynamicPropertyRegistrar dynamicPropertyRegistrar(MongoDBContainer mongoDBContainer, KeycloakContainer keycloakContainer, TestContainerProperties testContainerProperties) {
        return registry -> {
            MongoContainerConfiguration.registry(registry, mongoDBContainer);
            KeycloakContainerConfiguration.registry(registry, keycloakContainer, testContainerProperties);
        };
    }

}
