package ir.msob.jima.crud.ral.r2dbc.it.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.it.criteria.ProjectCriteria;
import ir.msob.jima.core.it.domain.ProjectDomain;
import ir.msob.jima.core.it.dto.ProjectDto;
import ir.msob.jima.core.it.security.ProjectUser;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.service.domain.operation.BeforeAfterOperationComponent;
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
        R extends R2dbcDomainCrudRepository<D, C>>
        implements BaseDomainCrudService<String, ProjectUser, D, DTO, C, R> {

    private final BeforeAfterOperationComponent beforeAfterOperationComponent;
    private final ObjectMapper objectMapper;
    private final R repository;

}
