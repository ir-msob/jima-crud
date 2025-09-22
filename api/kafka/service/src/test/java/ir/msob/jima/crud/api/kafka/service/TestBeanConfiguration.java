package ir.msob.jima.crud.api.kafka.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import ir.msob.jima.core.beans.properties.JimaProperties;
import ir.msob.jima.core.ral.kafka.test.KafkaContainerConfiguration;
import ir.msob.jima.core.ral.mongo.test.configuration.MongoContainerConfiguration;
import ir.msob.jima.security.ral.keycloak.test.KeycloakContainerConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.kafka.KafkaContainer;

@TestConfiguration
public class TestBeanConfiguration {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    /**
     * Registers dynamic properties for the Kafka container.
     *
     * @param kafkaContainer The Kafka container instance.
     * @return The DynamicPropertyRegistrar bean.
     */
    @Bean
    public DynamicPropertyRegistrar dynamicPropertyRegistrar(KafkaContainer kafkaContainer, MongoDBContainer mongoDBContainer, KeycloakContainer keycloakContainer, JimaProperties jimaProperties) {
        return registry -> {
            KafkaContainerConfiguration.registry(registry, kafkaContainer);
            MongoContainerConfiguration.registry(registry, mongoDBContainer);
            KeycloakContainerConfiguration.registry(registry, keycloakContainer, jimaProperties);
        };
    }

}
