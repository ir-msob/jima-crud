package ir.msob.jima.crud.sample.rsocket.base.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.ral.mongo.commons.query.QueryBuilder;
import ir.msob.jima.crud.sample.rsocket.base.criteria.ProjectCriteria;
import ir.msob.jima.crud.sample.rsocket.base.domain.ProjectDomain;
import ir.msob.jima.crud.sample.rsocket.base.dto.ProjectDto;
import ir.msob.jima.crud.sample.rsocket.base.repository.MongoCrudRepository;
import ir.msob.jima.crud.sample.rsocket.base.security.ProjectUser;
import ir.msob.jima.crud.service.domain.BaseCrudService;
import ir.msob.jima.crud.service.domain.BeforeAfterComponent;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

@Validated
public abstract class CrudService<
        D extends ProjectDomain,
        DTO extends ProjectDto,
        C extends ProjectCriteria,
        R extends MongoCrudRepository<D, C>
        >
        implements BaseCrudService<ObjectId, ProjectUser, D, DTO, C, QueryBuilder, R> {

    @Autowired
    private BeforeAfterComponent beforeAfterComponent;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private R repository;

    @Override
    public BeforeAfterComponent getBeforeAfterComponent() {
        return beforeAfterComponent;
    }

    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @Override
    public R getRepository() {
        return repository;
    }
}
