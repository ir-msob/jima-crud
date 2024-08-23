package ir.msob.jima.crud.sample.grpc.domain;

import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import ir.msob.jima.core.ral.mongo.test.configuration.MongoContainerConfiguration;
import ir.msob.jima.core.test.CoreTestData;
import ir.msob.jima.crud.api.grpc.commons.ReactorCrudServiceGrpc;
import ir.msob.jima.crud.sample.grpc.JimaApplication;
import ir.msob.jima.crud.sample.grpc.base.CrudGrpcResourceTest;
import ir.msob.jima.security.ral.keycloak.test.KeycloakContainerConfiguration;
import lombok.SneakyThrows;
import lombok.extern.apachecommons.CommonsLog;
import org.assertj.core.api.Assertions;
import org.bson.types.ObjectId;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;

@SpringBootTest(classes = {JimaApplication.class, MongoContainerConfiguration.class, KeycloakContainerConfiguration.class})
@ContextConfiguration
@Testcontainers
@CommonsLog
public class SampleDomainGrpcResourceIT extends CrudGrpcResourceTest<SampleDomain, SampleDto, SampleCriteria, SampleRepository, SampleService, SampleDomainDataProvider> {

    @Rule
    public final GrpcCleanupRule grpcCleanupRule = new GrpcCleanupRule();
    @Autowired
    SampleService service;
    @Autowired
    SampleDomainGrpcResource resource;
    private ReactorCrudServiceGrpc.ReactorCrudServiceStub reactorCrudServiceStub;

    @SneakyThrows
    @BeforeAll
    public static void beforeAll() {
        CoreTestData.init(new ObjectId(), new ObjectId());
    }

    private void registerService() throws IOException {
        String serverName = InProcessServerBuilder.generateName();
        grpcCleanupRule.register(InProcessServerBuilder
                .forName(serverName)
                .directExecutor()
                .addService(resource)
                .build()
                .start());
        reactorCrudServiceStub = ReactorCrudServiceGrpc
                .newReactorStub(grpcCleanupRule.register(InProcessChannelBuilder.forName(serverName).directExecutor().build()));
    }

    @SneakyThrows
    @BeforeEach
    public void beforeEach() {
        getDataProvider().cleanups();
        SampleDomainDataProvider.createNewDto();
        SampleDomainDataProvider.createMandatoryNewDto();
        registerService();
    }

    @Override
    public Class<SampleDomainGrpcResource> getResourceClass() {
        return SampleDomainGrpcResource.class;
    }

    @Override
    public ReactorCrudServiceGrpc.ReactorCrudServiceStub getReactorCrudServiceStub() {
        return reactorCrudServiceStub;
    }

    @Override
    public void assertMandatory(SampleDto before, SampleDto after) {
        Assertions.assertThat(after.getDomainMandatoryField()).isEqualTo(before.getDomainMandatoryField());
    }

    @Override
    public void assertAll(SampleDto before, SampleDto after) {
        assertMandatory(before, after);
        Assertions.assertThat(after.getDomainField()).isEqualTo(before.getDomainField());
    }
}
