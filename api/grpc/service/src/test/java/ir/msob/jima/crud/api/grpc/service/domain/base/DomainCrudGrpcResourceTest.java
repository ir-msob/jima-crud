package ir.msob.jima.crud.api.grpc.service.domain.base;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.Metadata;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import ir.msob.jima.core.beans.properties.JimaProperties;
import ir.msob.jima.core.commons.security.BaseTokenService;
import ir.msob.jima.core.ral.mongo.commons.query.QueryBuilder;
import ir.msob.jima.core.ral.mongo.it.criteria.ProjectCriteria;
import ir.msob.jima.core.ral.mongo.it.domain.ProjectDomain;
import ir.msob.jima.core.ral.mongo.it.dto.ProjectDto;
import ir.msob.jima.core.ral.mongo.it.security.ProjectUser;
import ir.msob.jima.crud.api.grpc.commons.CrudServiceGrpc;
import ir.msob.jima.crud.api.grpc.service.domain.TestDomainGrpcResourceDomain;
import ir.msob.jima.crud.api.grpc.test.domain.BaseDomainCrudGrpcResourceTest;
import ir.msob.jima.crud.ral.mongo.it.base.DomainCrudDataProvider;
import ir.msob.jima.crud.ral.mongo.it.base.DomainCrudService;
import ir.msob.jima.crud.ral.mongo.it.base.MongoDomainCrudRepository;
import ir.msob.jima.security.api.grpc.oauth2.AuthenticatingServerInterceptor;
import org.junit.Rule;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;


public abstract class DomainCrudGrpcResourceTest<
        D extends ProjectDomain,
        DTO extends ProjectDto,
        C extends ProjectCriteria,
        R extends MongoDomainCrudRepository<D, C>,
        S extends DomainCrudService<D, DTO, C, R>,
        DP extends DomainCrudDataProvider<D, DTO, C, R, S>>
        implements BaseDomainCrudGrpcResourceTest<String, ProjectUser, D, DTO, C, QueryBuilder, R, S, DP> {

    @Rule
    public final GrpcCleanupRule grpcCleanupRule = new GrpcCleanupRule();
    @Autowired
    BaseTokenService tokenService;
    @Autowired
    AuthenticatingServerInterceptor authenticationInterceptor;
    @Autowired
    DP dataProvider;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    JimaProperties jimaProperties;
    private CrudServiceGrpc.CrudServiceBlockingStub crudServiceBlockingStub;

    @Override
    public ProjectUser getSampleUser() {
        return getDataProvider().getSampleUser();
    }

    @Override
    public DP getDataProvider() {
        return dataProvider;
    }

    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @Override
    public TypeReference<Collection<String>> getIdsReferenceType() {
        return new TypeReference<Collection<String>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
    }

    @Override
    public JimaProperties getJimaProperties() {
        return jimaProperties;
    }

    @Override
    public CrudServiceGrpc.CrudServiceBlockingStub getCrudServiceBlockingStub() {
        return crudServiceBlockingStub;
    }

    protected void registerStub() throws IOException {
        String serverName = InProcessServerBuilder.generateName();
        grpcCleanupRule.register(InProcessServerBuilder
                .forName(serverName)
                .directExecutor()
                .addService(getResource())
                .intercept(authenticationInterceptor)
                .build()
                .start());

        String token = tokenService.getToken();
        Metadata metadata = new Metadata();
        metadata.put(Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER), "Bearer " + token);

        var interceptor = io.grpc.stub.MetadataUtils.newAttachHeadersInterceptor(metadata);

        crudServiceBlockingStub = CrudServiceGrpc
                .newBlockingStub(grpcCleanupRule.register(
                        InProcessChannelBuilder.forName(serverName).directExecutor().build()
                ))
                .withInterceptors(interceptor);

    }


    protected abstract TestDomainGrpcResourceDomain getResource();

}
