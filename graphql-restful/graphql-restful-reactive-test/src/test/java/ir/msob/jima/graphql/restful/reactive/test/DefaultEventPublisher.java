package ir.msob.jima.graphql.restful.reactive.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import ir.msob.jima.platform.api.channel.ChannelMessage;
import ir.msob.jima.platform.api.event.publisher.BaseEventPublisher;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.api.shared.ModelType;
import ir.msob.jima.platform.reactive.event.publisher.BaseEventReactivePublisher;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class DefaultEventPublisher implements BaseEventReactivePublisher {

    @Override
    public <USER extends BaseUser, DATA extends ModelType> Mono<@NonNull Void> send(String channel, ChannelMessage<USER, DATA> channelMessage, USER user) {
        return null;
    }

    @Override
    public <USER extends BaseUser, DATA extends ModelType> Mono<@NonNull Void> send(String channel, ChannelMessage<USER, DATA> channelMessage) throws JsonProcessingException {
        return null;
    }

    @Override
    public <USER extends BaseUser> Mono<@NonNull Void> send(String channel, Map<String, Object> channelMessage, USER user) {
        return null;
    }
}
