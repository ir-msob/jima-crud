package ir.msob.jima.crud.ral.mongo.it.testchild;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.commons.operation.BaseBeforeAfterDomainOperation;
import ir.msob.jima.core.it.security.ProjectUser;
import ir.msob.jima.core.ral.mongo.it.testchild.TestChildCriteria;
import ir.msob.jima.core.ral.mongo.it.testchild.TestChildDomain;
import ir.msob.jima.core.ral.mongo.it.testchild.TestChildDto;
import ir.msob.jima.crud.ral.mongo.it.base.ChildDomainCrudService;
import ir.msob.jima.crud.service.domain.operation.BeforeAfterOperationComponent;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class TestChildServiceDomain extends ChildDomainCrudService<TestChildDomain, TestChildDto, TestChildCriteria, TestChildRepository> {

    private final ModelMapper modelMapper;

    public TestChildServiceDomain(BeforeAfterOperationComponent beforeAfterOperationComponent, ObjectMapper objectMapper, TestChildRepository repository, ModelMapper modelMapper) {
        super(beforeAfterOperationComponent, objectMapper, repository);
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
    public List<BaseBeforeAfterDomainOperation<String, ProjectUser, TestChildDto, TestChildCriteria>> getBeforeAfterDomainOperations() {
        return Collections.emptyList();
    }

}
