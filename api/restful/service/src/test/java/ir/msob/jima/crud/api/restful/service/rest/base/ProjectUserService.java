package ir.msob.jima.crud.api.restful.service.rest.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.beans.properties.JimaProperties;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.security.it.BaseSecurityProjectUserService;
import ir.msob.jima.security.ral.keycloak.it.security.BaseKeycloakProjectUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectUserService implements BaseKeycloakProjectUserService, BaseSecurityProjectUserService {
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
    public <USER extends BaseUser> Optional<USER> getUser(Optional<String> optional) {
        return Optional.empty();
    }
}
