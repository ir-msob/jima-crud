package ir.msob.jima.crud.grpc.reactive.test.resource.domain.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.platform.api.embeddeddomain.model.relatedobject.relatedparty.RelatedPartyAbstract;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.testing.security.ProjectUser;
import ir.msob.jima.security.api.properties.SecurityProperties;
import ir.msob.jima.security.keycloak.testing.BaseKeycloakProjectUserService;
import ir.msob.jima.security.testing.BaseSecurityProjectUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * This class provides a base implementation for user-childdomain services within Jima projects.
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
    private final SecurityProperties securityProperties;

    /**
     * Reference to an ObjectMapper instance for potential JSON data processing.
     */
    private final ObjectMapper objectMapper;


    @Override
    public SecurityProperties getSecurityProperties() {
        return securityProperties;
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
     * @param token string containing the user identifier (might be null).
     * @return An empty `Optional` object indicating no user was found (placeholder implementation).
     */
    @Override
    public <USER extends BaseUser> USER getUser(String token) {
        return (USER) new ProjectUser();
    }

    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, RP extends RelatedPartyAbstract<ID>> RP getRelatedParty(USER user) {
        RelatedPartyAbstract<ID> relatedParty = new RelatedPartyAbstract<ID>() {
        };
        relatedParty.setRelatedId(user.getId());
        relatedParty.setName(user.getName());
        return (RP) relatedParty;
    }
}
