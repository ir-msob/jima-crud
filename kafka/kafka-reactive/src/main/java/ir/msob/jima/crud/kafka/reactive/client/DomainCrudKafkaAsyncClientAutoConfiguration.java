package ir.msob.jima.crud.kafka.reactive.client;

import ir.msob.jima.platform.reactive.event.publisher.BaseEventReactivePublisher;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class DomainCrudKafkaAsyncClientAutoConfiguration {

    @Bean
    public DomainCrudKafkaAsyncClient domainCrudKafkaAsyncClient(BaseEventReactivePublisher eventReactivePublisher) {
        return new DomainCrudKafkaAsyncClient(eventReactivePublisher);
    }
}
