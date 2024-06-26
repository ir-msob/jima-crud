package ir.msob.jima.crud.sample.rsocket.domain;

import io.rsocket.transport.netty.client.TcpClientTransport;
import ir.msob.jima.core.api.rsocket.commons.BaseRSocketRequesterBuilder;
import ir.msob.jima.core.beans.properties.JimaProperties;
import ir.msob.jima.core.commons.resource.BaseResource;
import ir.msob.jima.core.ral.mongo.test.configuration.MongoContainerConfiguration;
import ir.msob.jima.core.test.CoreTestData;
import ir.msob.jima.crud.sample.rsocket.JimaApplication;
import ir.msob.jima.crud.sample.rsocket.base.CrudRsocketResourceTest;
import ir.msob.jima.crud.sample.rsocket.base.security.ProjectUser;
import ir.msob.jima.security.ral.keycloak.test.KeycloakContainerConfiguration;
import lombok.SneakyThrows;
import lombok.extern.apachecommons.CommonsLog;
import org.assertj.core.api.Assertions;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(classes = {JimaApplication.class, MongoContainerConfiguration.class, KeycloakContainerConfiguration.class})
@ContextConfiguration
@Testcontainers
@CommonsLog
public class SampleDomainRsocketResourceIT extends CrudRsocketResourceTest<SampleDomain, SampleDto, SampleCriteria, SampleRepository, SampleService, SampleDomainDataProvider> {

    @Autowired
    SampleDomainRsocketResource testDomainRsocketResource;

    @Autowired
    BaseRSocketRequesterBuilder rSocketOauth2RequesterBuilder;

    RSocketRequester requester;

    @Value("${spring.rsocket.server.port}")
    Integer port;
    String host = "localhost";
    @Autowired
    JimaProperties jimaProperties;

    @SneakyThrows
    @BeforeAll
    public static void beforeAll() {
        CoreTestData.init(new ObjectId(), new ObjectId());

    }

    @SneakyThrows
    @BeforeEach
    public void beforeEach() {
        getDataProvider().cleanups();
        getDataProvider().createNewDto();
        getDataProvider().createMandatoryNewDto();
        requester = rSocketOauth2RequesterBuilder.getBuilder()
                .transport(TcpClientTransport.create(host, port));
    }

    @Override
    public RSocketRequester getRSocketRequester() {
        return requester;
    }

    @Override
    public Class<? extends BaseResource<ObjectId, ProjectUser>> getResourceClass() {
        return SampleDomainRsocketResource.class;
    }

    @Override
    public String getBaseUri() {
        return SampleDomainRsocketResource.BASE_URI;
    }

    @Override
    public void assertMandatory(SampleDto before, SampleDto after) {
        Assertions.assertThat(after.getDomainMandatoryField()).isEqualTo(before.getDomainMandatoryField());
    }

    @Override
    public void assertAll(SampleDto before, SampleDto after) {
        assertMandatory(before,after);
        Assertions.assertThat(after.getDomainField()).isEqualTo(before.getDomainField());
    }
}