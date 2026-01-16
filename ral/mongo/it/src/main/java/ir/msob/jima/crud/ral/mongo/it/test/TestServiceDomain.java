package ir.msob.jima.crud.ral.mongo.it.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.commons.operation.BaseBeforeAfterDomainOperation;
import ir.msob.jima.core.it.security.ProjectUser;
import ir.msob.jima.core.ral.mongo.it.test.TestCriteria;
import ir.msob.jima.core.ral.mongo.it.test.TestDomain;
import ir.msob.jima.core.ral.mongo.it.test.TestDto;
import ir.msob.jima.crud.ral.mongo.it.base.DomainCrudService;
import ir.msob.jima.crud.service.domain.operation.BeforeAfterOperationComponent;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class TestServiceDomain extends DomainCrudService<TestDomain, TestDto, TestCriteria, TestRepository> {

    private final ModelMapper modelMapper;

    public TestServiceDomain(BeforeAfterOperationComponent beforeAfterOperationComponent, ObjectMapper objectMapper, TestRepository repository, ModelMapper modelMapper) {
        super(beforeAfterOperationComponent, objectMapper, repository);
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
    public Collection<BaseBeforeAfterDomainOperation<String, ProjectUser, TestDto, TestCriteria>> getBeforeAfterDomainOperations() {
        return Collections.emptyList();
    }

}
