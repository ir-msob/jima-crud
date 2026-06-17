package ir.msob.jima.crud.restful.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import ir.msob.jima.platform.api.channel.ChannelMessage;
import ir.msob.jima.platform.api.event.publisher.BaseEventPublisher;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.api.shared.ModelType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DefaultEventPublisher implements BaseEventPublisher {
    @Override
    public <USER extends BaseUser, DATA extends ModelType> void send(String channel, ChannelMessage<USER, DATA> channelMessage, USER user) {

    }

    @Override
    public <USER extends BaseUser, DATA extends ModelType> void send(String channel, ChannelMessage<USER, DATA> channelMessage) throws JsonProcessingException {

    }

    @Override
    public <USER extends BaseUser> void send(String channel, Map<String, Object> channelMessage, USER user) {

    }
}
