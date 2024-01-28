package ir.msob.jima.crud.api.restful.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.beans.configuration.JimaConfigProperties;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.ral.mongo.it.security.BaseMongoProjectUserService;
import ir.msob.jima.security.api.restful.it.BaseSecurityProjectUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectUserService implements BaseMongoProjectUserService, BaseSecurityProjectUserService {
    private final JimaConfigProperties jimaConfigProperties;
    private final ObjectMapper objectMapper;

    @Override
    public JimaConfigProperties getJimaConfigProperties() {
        return jimaConfigProperties;
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
