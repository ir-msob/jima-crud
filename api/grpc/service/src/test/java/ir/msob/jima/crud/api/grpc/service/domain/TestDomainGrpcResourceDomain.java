package ir.msob.jima.crud.api.grpc.service.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.ral.mongo.it.test.TestCriteria;
import ir.msob.jima.core.ral.mongo.it.test.TestDomain;
import ir.msob.jima.core.ral.mongo.it.test.TestDto;
import ir.msob.jima.crud.api.grpc.service.domain.base.DomainCrudGrpcResource;
import ir.msob.jima.crud.api.grpc.service.domain.base.ProjectUserService;
import ir.msob.jima.crud.ral.mongo.it.test.TestRepository;
import ir.msob.jima.crud.ral.mongo.it.test.TestServiceDomain;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class TestDomainGrpcResourceDomain extends DomainCrudGrpcResource<TestDomain, TestDto, TestCriteria, TestRepository, TestServiceDomain> {

    public TestDomainGrpcResourceDomain(ObjectMapper objectMapper, ProjectUserService projectUserService, TestServiceDomain service) {
        super(objectMapper, projectUserService, service);
    }
}
