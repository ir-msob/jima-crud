package ir.msob.jima.crud.sample.rsocket.domain;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.*;
import ir.msob.jima.core.commons.model.dto.ModelType;
import ir.msob.jima.core.commons.model.scope.Resource;
import ir.msob.jima.crud.sample.rsocket.base.Microservices;
import ir.msob.jima.crud.sample.rsocket.base.resource.CrudRsocketResource;
import ir.msob.jima.crud.sample.rsocket.base.security.ProjectUser;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ir.msob.jima.core.commons.resource.ResourceType.RSOCKET_RESOURCE_TYPE;

@Controller
@MessageMapping(SampleDomainRsocketResource.BASE_URI)
@RequiredArgsConstructor
@Resource(value = TestDomain.DOMAIN_URI, type = ResourceType.RESTFUL)
public class SampleDomainRsocketResource extends CrudRsocketResource<SampleDomain, SampleDto, SampleCriteria, SampleRepository, SampleService> {
    public static final String BASE_URI = "api." + Microservices.VERSION + "." + SampleDomain.DOMAIN_URI;
}
