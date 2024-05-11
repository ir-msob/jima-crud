package ir.msob.jima.crud.sample.graphql.restful.domain;

import ir.msob.jima.core.commons.operation.BaseBeforeAfterDomainOperation;
import ir.msob.jima.crud.sample.graphql.restful.base.security.ProjectUser;
import ir.msob.jima.crud.sample.graphql.restful.base.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SampleService extends CrudService<SampleDomain, SampleDto, SampleCriteria, SampleRepository> {

    private final ModelMapper modelMapper;

    @Override
    public SampleDto toDto(SampleDomain domain, Optional<ProjectUser> user) {
        return modelMapper.map(domain, SampleDto.class);
    }

    @Override
    public SampleDomain toDomain(SampleDto dto, Optional<ProjectUser> user) {
        return dto;
    }

    @Override
    public Collection<BaseBeforeAfterDomainOperation<ObjectId, ProjectUser, SampleDto, SampleCriteria>> getBeforeAfterDomainOperations() {
        return Collections.emptyList();
    }

}
