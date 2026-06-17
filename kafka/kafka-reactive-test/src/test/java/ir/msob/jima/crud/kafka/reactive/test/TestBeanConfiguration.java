package ir.msob.jima.crud.kafka.reactive.test;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import ir.msob.jima.platform.api.properties.TestContainerProperties;
import ir.msob.jima.platform.kafka.testing.configuration.KafkaContainerConfiguration;
import ir.msob.jima.platform.mongo.testing.configuration.MongoContainerConfiguration;
import ir.msob.jima.security.keycloak.testing.configuration.KeycloakContainerConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.mongodb.MongoDBContainer;

@TestConfiguration
public class TestBeanConfiguration {

    /**
     * Registers dynamic properties for the Kafka container.
     *
     * @param kafkaContainer The Kafka container instance.
     * @return The DynamicPropertyRegistrar bean.
     */
    @Bean
    public DynamicPropertyRegistrar dynamicPropertyRegistrar(KafkaContainer kafkaContainer, MongoDBContainer mongoDBContainer, KeycloakContainer keycloakContainer, TestContainerProperties testContainerProperties) {
        return registry -> {
            KafkaContainerConfiguration.registry(registry, kafkaContainer);
            MongoContainerConfiguration.registry(registry, mongoDBContainer);
            KeycloakContainerConfiguration.registry(registry, keycloakContainer, testContainerProperties);
        };
    }
}
