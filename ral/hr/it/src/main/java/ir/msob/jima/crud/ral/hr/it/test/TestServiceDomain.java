package ir.msob.jima.crud.ral.hr.it.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.commons.operation.BaseBeforeAfterDomainOperation;
import ir.msob.jima.core.it.security.ProjectUser;
import ir.msob.jima.core.ral.hr.it.test.TestCriteria;
import ir.msob.jima.core.ral.hr.it.test.TestDomain;
import ir.msob.jima.core.ral.hr.it.test.TestDto;
import ir.msob.jima.crud.ral.hr.it.base.DomainCrudService;
import ir.msob.jima.crud.service.domain.BeforeAfterComponent;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class TestServiceDomain extends DomainCrudService<TestDomain, TestDto, TestCriteria, TestRepository> {

    private final ModelMapper modelMapper;

    public TestServiceDomain(BeforeAfterComponent beforeAfterComponent, ObjectMapper objectMapper, TestRepository repository, ModelMapper modelMapper) {
        super(beforeAfterComponent, objectMapper, repository);
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
