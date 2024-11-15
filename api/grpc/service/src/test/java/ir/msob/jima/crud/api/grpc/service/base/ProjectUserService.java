package ir.msob.jima.crud.api.grpc.service.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.beans.properties.JimaProperties;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.ral.mongo.it.security.ProjectUser;
import ir.msob.jima.security.it.BaseSecurityProjectUserService;
import ir.msob.jima.security.ral.keycloak.it.security.BaseKeycloakProjectUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * This class provides a base implementation for user-related services within Jima projects.
 * <p>
 * It implements interfaces for both Keycloak and a generic security project user service.
 * This allows for potential customization based on the chosen security provider.
 * <p>
 * However, the current implementation of `getUser` method simply returns an empty `Optional`.
 */
@Service
@RequiredArgsConstructor
public class ProjectUserService implements BaseKeycloakProjectUserService, BaseSecurityProjectUserService {

    /**
     * Reference to the Jima properties configuration object.
     */
    private final JimaProperties jimaProperties;

    /**
     * Reference to an ObjectMapper instance for potential JSON data processing.
     */
    private final ObjectMapper objectMapper;

    /**
     * Retrieves the configured Jima properties.
     *
     * @return The JimaProperties object containing project configuration details.
     */
    @Override
    public JimaProperties getJimaProperties() {
        return jimaProperties;
    }

    /**
     * Retrieves the configured ObjectMapper instance.
     *
     * @return The ObjectMapper instance used for JSON data processing (if applicable).
     */
    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * This method attempts to retrieve a user object based on the provided optional user identifier.
     * <p>
     * **Current implementation always returns an empty Optional.**
     * This behavior needs to be customized based on the chosen security provider
     * (e.g., Keycloak integration) to fetch user information.
     *
     * @param A string containing the user identifier (might be null).
     * @return An empty `Optional` object indicating no user was found (placeholder implementation).
     */
    @Override
    public <USER extends BaseUser> USER getUser(String token) {
        return (USER) new ProjectUser();
    }
}
