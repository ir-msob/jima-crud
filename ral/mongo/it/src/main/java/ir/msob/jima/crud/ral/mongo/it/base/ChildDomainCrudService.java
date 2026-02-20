package ir.msob.jima.crud.ral.mongo.it.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.it.childcriteria.ProjectChildCriteria;
import ir.msob.jima.core.it.childdomain.ProjectChildDomain;
import ir.msob.jima.core.it.childdto.ProjectChildDto;
import ir.msob.jima.core.it.criteria.ProjectCriteria;
import ir.msob.jima.core.it.domain.ProjectDomain;
import ir.msob.jima.core.it.dto.ProjectDto;
import ir.msob.jima.core.it.security.ProjectUser;
import ir.msob.jima.crud.service.childdomain.BaseChildDomainCrudService;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.service.domain.operation.BeforeAfterOperationComponent;
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


    private final BeforeAfterOperationComponent beforeAfterOperationComponent;
    private final ObjectMapper objectMapper;
    private final R repository;

}
