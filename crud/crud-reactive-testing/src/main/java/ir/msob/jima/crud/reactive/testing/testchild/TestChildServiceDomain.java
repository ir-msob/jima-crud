package ir.msob.jima.crud.reactive.testing.testchild;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.crud.reactive.service.domain.operation.ReactiveLifecycleOperationComponent;
import ir.msob.jima.crud.reactive.testing.base.ChildDomainCrudService;
import ir.msob.jima.platform.mongo.testing.testchild.TestChildCriteria;
import ir.msob.jima.platform.mongo.testing.testchild.TestChildDomain;
import ir.msob.jima.platform.mongo.testing.testchild.TestChildDto;
import ir.msob.jima.platform.reactive.operation.BaseReactiveDomainLifecycleOperation;
import ir.msob.jima.platform.testing.security.ProjectUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestChildServiceDomain extends ChildDomainCrudService<TestChildDomain, TestChildDto, TestChildCriteria, TestChildRepository> {

    private final ModelMapper modelMapper;

    public TestChildServiceDomain(ReactiveLifecycleOperationComponent reactiveLifecycleOperationComponent, ObjectMapper objectMapper, TestChildRepository repository, ModelMapper modelMapper) {
        super(reactiveLifecycleOperationComponent, objectMapper, repository);
        this.modelMapper = modelMapper;
    }


    @Override
    public TestChildDto toDto(TestChildDomain domain, ProjectUser user) {
        return modelMapper.map(domain, TestChildDto.class);
    }

    @Override
    public TestChildDomain toDomain(TestChildDto dto, ProjectUser user) {
        return dto;
    }


    @Override
    public List<BaseReactiveDomainLifecycleOperation<String, ProjectUser, TestChildDto, TestChildCriteria>> getDomainLifecycleOperation() {
        return List.of();
    }
}
