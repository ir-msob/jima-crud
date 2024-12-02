package ir.msob.jima.crud.ral.mongo.it.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.commons.operation.BaseBeforeAfterDomainOperation;
import ir.msob.jima.core.ral.mongo.it.security.ProjectUser;
import ir.msob.jima.core.ral.mongo.it.test.TestCriteria;
import ir.msob.jima.core.ral.mongo.it.test.TestDomain;
import ir.msob.jima.core.ral.mongo.it.test.TestDto;
import ir.msob.jima.crud.ral.mongo.it.base.CrudService;
import ir.msob.jima.crud.service.domain.BeforeAfterComponent;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class TestService extends CrudService<TestDomain, TestDto, TestCriteria, TestRepository> {

    private final ModelMapper modelMapper;

    public TestService(BeforeAfterComponent beforeAfterComponent, ObjectMapper objectMapper, TestRepository repository, ModelMapper modelMapper) {
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
    public Collection<BaseBeforeAfterDomainOperation<ObjectId, ProjectUser, TestDto, TestCriteria>> getBeforeAfterDomainOperations() {
        return Collections.emptyList();
    }

}
