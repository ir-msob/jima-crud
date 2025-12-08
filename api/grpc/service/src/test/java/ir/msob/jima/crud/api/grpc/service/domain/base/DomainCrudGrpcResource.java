package ir.msob.jima.crud.api.grpc.service.domain.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.commons.security.BaseUserService;
import ir.msob.jima.core.it.criteria.ProjectCriteria;
import ir.msob.jima.core.it.domain.ProjectDomain;
import ir.msob.jima.core.it.dto.ProjectDto;
import ir.msob.jima.core.it.security.ProjectUser;
import ir.msob.jima.crud.api.grpc.service.domain.BaseDomainCrudGrpcResource;
import ir.msob.jima.crud.ral.mongo.it.base.DomainCrudService;
import ir.msob.jima.crud.ral.mongo.it.base.MongoDomainCrudRepository;

/**
 * @param <D>
 * @param <DTO>
 * @param <C>
 * @param <R>
 * @param <S>
 * @author Yaqub Abdi
 */
public abstract class DomainCrudGrpcResource<
        D extends ProjectDomain,
        DTO extends ProjectDto,
        C extends ProjectCriteria,
        R extends MongoDomainCrudRepository<D>,
        S extends DomainCrudService<D, DTO, C, R>
        > extends BaseDomainCrudGrpcResource<String, ProjectUser, D, DTO, C, R, S> {

    private final ProjectUserService projectUserService;
    private final S service;

    public DomainCrudGrpcResource(ObjectMapper objectMapper, ProjectUserService projectUserService, S service) {
        super(objectMapper);
        this.projectUserService = projectUserService;
        this.service = service;
    }

    @Override
    public BaseUserService getUserService() {
        return projectUserService;
    }

    @Override
    public S getService() {
        return service;
    }
}
