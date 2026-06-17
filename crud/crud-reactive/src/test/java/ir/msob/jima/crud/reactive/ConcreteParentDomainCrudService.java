package ir.msob.jima.crud.reactive;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.crud.reactive.service.domain.ParentDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.service.domain.operation.ReactiveLifecycleOperationComponent;
import ir.msob.jima.platform.api.domain.criteria.SampleDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.SampleDomain;
import ir.msob.jima.platform.api.domain.dto.SampleDto;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.operation.BaseReactiveDomainLifecycleOperation;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;

import java.util.List;

public class ConcreteParentDomainCrudService implements
        ParentDomainCrudReactiveService<String,
                BaseUser,
                SampleDomain<String>,
                SampleDto<String>,
                SampleDomainCriteria<String>,
                BaseReactiveRepository<String, SampleDomain<String>, SampleDomainCriteria<String>>> {


    @Override
    public BaseReactiveRepository<String, SampleDomain<String>, SampleDomainCriteria<String>> getRepository() {
        return null;
    }

    @Override
    public SampleDto<String> toDto(SampleDomain<String> domain, BaseUser user) {
        return null;
    }

    @Override
    public SampleDomain<String> toDomain(SampleDto<String> dto, BaseUser user) {
        return null;
    }

    @Override
    public ReactiveLifecycleOperationComponent getReactiveLifecycleOperationComponent() {
        return null;
    }

    @Override
    public List<BaseReactiveDomainLifecycleOperation<String, BaseUser, SampleDto<String>, SampleDomainCriteria<String>>> getDomainLifecycleOperation() {
        return null;
    }

    @Override
    public ObjectMapper getObjectMapper() {
        return null;
    }
}
