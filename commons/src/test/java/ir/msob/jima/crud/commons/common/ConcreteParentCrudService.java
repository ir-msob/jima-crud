package ir.msob.jima.crud.commons.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.commons.data.BaseRepository;
import ir.msob.jima.core.commons.model.criteria.SampleCriteria;
import ir.msob.jima.core.commons.model.domain.SampleDomain;
import ir.msob.jima.core.commons.model.dto.SampleDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.BaseBeforeAfterService;
import ir.msob.jima.crud.commons.ParentCrudService;

import java.util.Optional;

public class ConcreteParentCrudService implements
        ParentCrudService<String,
                BaseUser<String>,
                SampleDomain<String>,
                SampleDto<String>,
                SampleCriteria<String>,
                BaseRepository<String,
                        BaseUser<String>,
                        SampleDomain<String>>> {


    @Override
    public BaseRepository<String, BaseUser<String>, SampleDomain<String>> getRepository() {
        return null;
    }

    @Override
    public SampleDto<String> toDto(SampleDomain<String> domain, Optional<BaseUser<String>> stringBaseUser) {
        return null;
    }

    @Override
    public SampleDomain<String> toDomain(SampleDto<String> dto, Optional<BaseUser<String>> stringBaseUser) {
        return null;
    }

    @Override
    public BaseBeforeAfterService getBeforeAfterComponent() {
        return null;
    }

    @Override
    public ObjectMapper getObjectMapper() {
        return null;
    }
}
