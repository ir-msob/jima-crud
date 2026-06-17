package ir.msob.jima.crud.testing.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.crud.api.service.domain.operation.LifecycleOperationComponent;
import ir.msob.jima.crud.testing.base.DomainCrudService;
import ir.msob.jima.platform.api.operation.BaseDomainLifecycleOperation;
import ir.msob.jima.platform.mongo.testing.test.TestCriteria;
import ir.msob.jima.platform.mongo.testing.test.TestDomain;
import ir.msob.jima.platform.mongo.testing.test.TestDto;
import ir.msob.jima.platform.testing.security.ProjectUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestDomainService extends DomainCrudService<TestDomain, TestDto, TestCriteria, TestRepository> {

    private final ModelMapper modelMapper;

    public TestDomainService(LifecycleOperationComponent lifecycleOperationComponent, ObjectMapper objectMapper, TestRepository repository, ModelMapper modelMapper) {
        super(lifecycleOperationComponent, objectMapper, repository);
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
    public List<BaseDomainLifecycleOperation<String, ProjectUser, TestDto, TestCriteria>> getDomainLifecycleOperation() {
        return List.of();
    }
}
