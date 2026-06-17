package ir.msob.jima.crud.rsocket.reactive.test;

import ir.msob.jima.platform.rsocket.api.BaseRSocketRequesterLoadBalancer;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;

@Component
public class FakeRSocketRequesterLoadBalancer implements BaseRSocketRequesterLoadBalancer {
    @Override
    public RSocketRequester getRequester(String applicationName, Object... candidateHandlers) {
        return null;
    }
}
