package ir.msob.jima.crud.grpc.reactive.test.resource.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.crud.grpc.reactive.test.resource.domain.base.DomainCrudGrpcResource;
import ir.msob.jima.crud.grpc.reactive.test.resource.domain.base.ProjectUserService;
import ir.msob.jima.crud.reactive.testing.test.TestDomainService;
import ir.msob.jima.crud.reactive.testing.test.TestRepository;
import ir.msob.jima.platform.mongo.testing.test.TestCriteria;
import ir.msob.jima.platform.mongo.testing.test.TestDomain;
import ir.msob.jima.platform.mongo.testing.test.TestDto;
import ir.msob.jima.platform.mongo.testing.test.TestDtoTypeReference;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class TestDomainGrpcResourceDomain extends DomainCrudGrpcResource<TestDomain, TestDto, TestCriteria, TestRepository, TestDomainService>
        implements TestDtoTypeReference {

    public TestDomainGrpcResourceDomain(ObjectMapper objectMapper, ProjectUserService projectUserService, TestDomainService service) {
        super(objectMapper, projectUserService, service);
    }
}
