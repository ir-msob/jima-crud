package ir.msob.jima.crud.reactive.testing.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.crud.reactive.service.domain.operation.ReactiveLifecycleOperationComponent;
import ir.msob.jima.crud.reactive.testing.base.DomainCrudService;
import ir.msob.jima.platform.mongo.testing.test.TestCriteria;
import ir.msob.jima.platform.mongo.testing.test.TestDomain;
import ir.msob.jima.platform.mongo.testing.test.TestDto;
import ir.msob.jima.platform.reactive.operation.BaseReactiveDomainLifecycleOperation;
import ir.msob.jima.platform.testing.security.ProjectUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestDomainService extends DomainCrudService<TestDomain, TestDto, TestCriteria, TestRepository> {

    private final ModelMapper modelMapper;

    public TestDomainService(ReactiveLifecycleOperationComponent reactiveLifecycleOperationComponent, ObjectMapper objectMapper, TestRepository repository, ModelMapper modelMapper) {
        super(reactiveLifecycleOperationComponent, objectMapper, repository);
        this.modelMapper = modelMapper;
    }


    @Override
    public TestDto toDto(TestDomain domain, ProjectUser user) {
        return modelMapper.map(domain, TestDto.class);
    }

    @Override
    public TestDomain toDomain(TestDto dto, ProjectUser user) {
        return dto;
    }


    @Override
    public List<BaseReactiveDomainLifecycleOperation<String, ProjectUser, TestDto, TestCriteria>> getDomainLifecycleOperation() {
        return List.of();
    }
}
