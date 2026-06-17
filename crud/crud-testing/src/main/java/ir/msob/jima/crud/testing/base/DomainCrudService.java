package ir.msob.jima.crud.testing.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.crud.api.service.domain.operation.LifecycleOperationComponent;
import ir.msob.jima.crud.core.service.domain.BaseDomainCrudService;
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
        R extends MongoDomainCrudRepository<D, C>>
        implements BaseDomainCrudService<String, ProjectUser, D, DTO, C, R> {


    private final LifecycleOperationComponent lifecycleOperationComponent;
    private final ObjectMapper objectMapper;
    private final R repository;

}
