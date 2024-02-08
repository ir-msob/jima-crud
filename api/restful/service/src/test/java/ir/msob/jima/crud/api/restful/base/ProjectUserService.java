package ir.msob.jima.crud.api.restful.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.beans.properties.JimaProperties;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.ral.mongo.it.security.BaseMongoProjectUserService;
import ir.msob.jima.security.it.BaseSecurityProjectUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectUserService implements BaseMongoProjectUserService, BaseSecurityProjectUserService {
    private final JimaProperties jimaProperties;
    private final ObjectMapper objectMapper;

    @Override
    public JimaProperties getJimaProperties() {
        return jimaProperties;
    }

    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>> Optional<USER> getUser(Optional<String> optional) {
        return Optional.empty();
    }
}
