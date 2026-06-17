package ir.msob.jima.crud.grpc.reactive.test.resource.domain.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.crud.grpc.reactive.service.domain.BaseDomainCrudGrpcResource;
import ir.msob.jima.crud.reactive.testing.base.DomainCrudService;
import ir.msob.jima.crud.reactive.testing.base.MongoDomainCrudReactiveRepository;
import ir.msob.jima.platform.api.security.BaseUserService;
import ir.msob.jima.platform.testing.criteria.ProjectCriteria;
import ir.msob.jima.platform.testing.domain.ProjectDomain;
import ir.msob.jima.platform.testing.dto.ProjectDto;
import ir.msob.jima.platform.testing.security.ProjectUser;

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
        R extends MongoDomainCrudReactiveRepository<D, C>,
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
