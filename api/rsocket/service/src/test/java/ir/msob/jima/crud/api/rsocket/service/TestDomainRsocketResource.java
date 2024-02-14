package ir.msob.jima.crud.api.rsocket.service;


import com.fasterxml.jackson.core.type.TypeReference;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.DtosMessage;
import ir.msob.jima.core.ral.mongo.it.Microservices;
import ir.msob.jima.core.ral.mongo.it.security.ProjectUser;
import ir.msob.jima.core.ral.mongo.it.test.TestCriteria;
import ir.msob.jima.core.ral.mongo.it.test.TestDomain;
import ir.msob.jima.core.ral.mongo.it.test.TestDto;
import ir.msob.jima.crud.api.rsocket.service.base.CrudRsocketResource;
import ir.msob.jima.crud.ral.mongo.it.test.TestRepository;
import ir.msob.jima.crud.ral.mongo.it.test.TestService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Type;


@Controller
@MessageMapping(TestDomainRsocketResource.BASE_URI)
@RequiredArgsConstructor
public class TestDomainRsocketResource extends CrudRsocketResource<TestDomain, TestDto, TestCriteria, TestRepository, TestService> {

    public static final String BASE_URI = "api." + Microservices.VERSION + "." + TestDomain.DOMAIN_URI;

    @Override
    public TypeReference<ChannelMessage<ObjectId, ProjectUser, DtosMessage<ObjectId, TestDto>>> getDtosReferenceType() {
        return new TypeReference<ChannelMessage<ObjectId, ProjectUser, DtosMessage<ObjectId, TestDto>>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
    }
}
