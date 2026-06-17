package ir.msob.jima.crud.reactive.testing.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.service.domain.operation.ReactiveLifecycleOperationComponent;
import ir.msob.jima.platform.testing.criteria.ProjectCriteria;
import ir.msob.jima.platform.testing.domain.ProjectDomain;
import ir.msob.jima.platform.testing.dto.ProjectDto;
import ir.msob.jima.platform.testing.security.ProjectUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Getter
@Validated
@RequiredArgsConstructor
public abstract class DomainCrudService<
        D extends ProjectDomain,
        DTO extends ProjectDto,
        C extends ProjectCriteria,
        R extends MongoDomainCrudReactiveRepository<D, C>>
        implements BaseDomainCrudReactiveService<String, ProjectUser, D, DTO, C, R> {


    private final ReactiveLifecycleOperationComponent reactiveLifecycleOperationComponent;
    private final ObjectMapper objectMapper;
    private final R repository;

}
