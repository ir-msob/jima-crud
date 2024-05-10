package ir.msob.jima.crud.sample.rsocket.domain;

import ir.msob.jima.core.ral.mongo.commons.query.QueryBuilder;
import ir.msob.jima.crud.sample.rsocket.base.repository.MongoCrudRepository;
import ir.msob.jima.crud.sample.rsocket.base.security.ProjectUser;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SampleRepository extends MongoCrudRepository<SampleDomain, SampleCriteria> {
    @Override
    public QueryBuilder criteria(QueryBuilder query, SampleCriteria criteria, Optional<ProjectUser> projectUser) {
        return super.criteria(query, criteria, projectUser);
    }

}

