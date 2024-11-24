package ir.msob.jima.crud.sample.grpc.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.ral.mongo.commons.query.QueryBuilder;
import ir.msob.jima.crud.api.grpc.test.domain.BaseCrudGrpcResourceTest;
import ir.msob.jima.crud.sample.grpc.base.criteria.ProjectCriteria;
import ir.msob.jima.crud.sample.grpc.base.domain.ProjectDomain;
import ir.msob.jima.crud.sample.grpc.base.dto.ProjectDto;
import ir.msob.jima.crud.sample.grpc.base.repository.MongoCrudRepository;
import ir.msob.jima.crud.sample.grpc.base.security.ProjectUser;
import ir.msob.jima.crud.sample.grpc.base.security.ProjectUserService;
import ir.msob.jima.crud.sample.grpc.base.service.CrudService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;


public abstract class CrudGrpcResourceTest<
        D extends ProjectDomain,
        DTO extends ProjectDto,
        C extends ProjectCriteria,
        R extends MongoCrudRepository<D, C>,
        S extends CrudService<D, DTO, C, R>,
        DP extends CrudDataProvider<D, DTO, C, R, S>>
        implements BaseCrudGrpcResourceTest<ObjectId, ProjectUser, D, DTO, C, QueryBuilder, R, S, DP> {

    @Autowired
    DP dataProvider;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ProjectUserService projectUserService;

    @Override
    public ProjectUser getSampleUser() {
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


}
