package ir.msob.jima.crud.grpc.reactive.test.resource.domain.base;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.ManagedChannel;
import io.grpc.Metadata;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.MetadataUtils;
import io.grpc.testing.GrpcCleanupRule;
import ir.msob.jima.crud.grpc.reactive.proto.CrudServiceGrpc;
import ir.msob.jima.crud.grpc.reactive.test.resource.domain.BaseDomainCrudGrpcReactiveResourceTest;
import ir.msob.jima.crud.grpc.reactive.test.resource.domain.TestDomainGrpcResourceDomain;
import ir.msob.jima.crud.reactive.testing.base.DomainCrudDataProvider;
import ir.msob.jima.crud.reactive.testing.base.DomainCrudService;
import ir.msob.jima.crud.reactive.testing.base.MongoDomainCrudReactiveRepository;
import ir.msob.jima.platform.api.security.BaseTokenService;
import ir.msob.jima.platform.testing.criteria.ProjectCriteria;
import ir.msob.jima.platform.testing.domain.ProjectDomain;
import ir.msob.jima.platform.testing.dto.ProjectDto;
import ir.msob.jima.platform.testing.security.ProjectUser;
import ir.msob.jima.security.grpc.autoconfigure.grpc.AuthenticatingServerInterceptor;
import org.jetbrains.annotations.NotNull;
import org.junit.Rule;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;


public abstract class DomainCrudGrpcResourceTest<
        D extends ProjectDomain,
        DTO extends ProjectDto,
        C extends ProjectCriteria,
        R extends MongoDomainCrudReactiveRepository<D, C>,
        S extends DomainCrudService<D, DTO, C, R>,
        DP extends DomainCrudDataProvider<D, DTO, C, R, S>>
        implements BaseDomainCrudGrpcReactiveResourceTest<String, ProjectUser, D, DTO, C, R, S, DP> {

    @Rule
    public final GrpcCleanupRule grpcCleanupRule = new GrpcCleanupRule();
    protected ManagedChannel channel;
    protected CrudServiceGrpc.CrudServiceBlockingStub crudServiceBlockingStub;
    @Autowired
    BaseTokenService tokenService;
    @Autowired
    AuthenticatingServerInterceptor authenticationInterceptor;
    @Autowired
    DP dataProvider;
    @Autowired
    ObjectMapper objectMapper;


    private static @NotNull Metadata prepareMetadata(String token) {
        Metadata metadata = new Metadata();
        metadata.put(Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER), "Bearer " + token);
        return metadata;
    }

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

        channel = grpcCleanupRule.register(InProcessChannelBuilder
                .forName(serverName)
                .directExecutor()
                .build());

        String token = tokenService.getToken();
        Metadata metadata = prepareMetadata(token);

        crudServiceBlockingStub = CrudServiceGrpc
                .newBlockingStub(channel)
                .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(metadata));

    }

    protected abstract TestDomainGrpcResourceDomain getResource();

    protected void channelShutdownNow() {
        if (channel != null && !channel.isShutdown()) {
            channel.shutdownNow();
        }
    }
}
