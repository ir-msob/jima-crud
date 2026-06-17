package ir.msob.jima.crud.reactive.testing.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.crud.reactive.service.childdomain.BaseChildDomainCrudService;
import ir.msob.jima.crud.reactive.service.domain.operation.ReactiveLifecycleOperationComponent;
import ir.msob.jima.platform.testing.childcriteria.ProjectChildCriteria;
import ir.msob.jima.platform.testing.childdomain.ProjectChildDomain;
import ir.msob.jima.platform.testing.childdto.ProjectChildDto;
import ir.msob.jima.platform.testing.security.ProjectUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Getter
@Validated
@RequiredArgsConstructor
public abstract class ChildDomainCrudService<
        D extends ProjectChildDomain,
        DTO extends ProjectChildDto,
        C extends ProjectChildCriteria,
        R extends MongoChildDomainCrudRepository<D, C>>
        implements BaseChildDomainCrudService<String, ProjectUser, D, DTO, C, R> {


    private final ReactiveLifecycleOperationComponent reactiveLifecycleOperationComponent;
    private final ObjectMapper objectMapper;
    private final R repository;

}
