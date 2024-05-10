package ir.msob.jima.crud.sample.rsocket.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.api.rsocket.commons.BaseRSocketRequesterMetadata;
import ir.msob.jima.core.ral.mongo.commons.query.QueryBuilder;
import ir.msob.jima.crud.api.rsocket.test.BaseCrudRsocketResourceTest;
import ir.msob.jima.crud.sample.rsocket.base.criteria.ProjectCriteria;
import ir.msob.jima.crud.sample.rsocket.base.domain.ProjectDomain;
import ir.msob.jima.crud.sample.rsocket.base.dto.ProjectDto;
import ir.msob.jima.crud.sample.rsocket.base.repository.MongoCrudRepository;
import ir.msob.jima.crud.sample.rsocket.base.security.ProjectUser;
import ir.msob.jima.crud.sample.rsocket.base.security.ProjectUserService;
import ir.msob.jima.crud.sample.rsocket.base.service.CrudService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public abstract class CrudRsocketResourceTest<
        D extends ProjectDomain,
        DTO extends ProjectDto,
        C extends ProjectCriteria,
        R extends MongoCrudRepository<D, C>,
        S extends CrudService<D, DTO, C, R>,
        DP extends CrudDataProvider<D, DTO, C, R, S>>
        implements BaseCrudRsocketResourceTest<ObjectId, ProjectUser, D, DTO, C, QueryBuilder, R, S, DP> {

    @Autowired
    DP dataProvider;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ProjectUserService projectUserService;
    @Autowired
    BaseRSocketRequesterMetadata socketRequesterMetadata;

    @Override
    public Optional<ProjectUser> getSampleUser() {
        return getDataProvider().getSampleUser();
    }

    @Override
    public DP getDataProvider() {
        return dataProvider;
    }

    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @Override
    public BaseRSocketRequesterMetadata getRSocketRequesterMetadata() {
        return socketRequesterMetadata;
    }

}
