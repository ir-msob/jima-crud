package ir.msob.jima.crud.restful.reactive.test.resource.domain.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.platform.api.embeddeddomain.model.relatedobject.relatedparty.RelatedPartyAbstract;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.testing.security.ProjectUser;
import ir.msob.jima.security.api.properties.SecurityProperties;
import ir.msob.jima.security.keycloak.testing.BaseKeycloakProjectUserService;
import ir.msob.jima.security.testing.BaseSecurityProjectUserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
@RequiredArgsConstructor
public class ProjectUserService implements BaseKeycloakProjectUserService, BaseSecurityProjectUserService {
    @Getter
    private final SecurityProperties securityProperties;
    @Getter
    private final ObjectMapper objectMapper;

    @Override
    public <USER extends BaseUser> USER getUser(String token) {
        return (USER) new ProjectUser();
    }

    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, RP extends RelatedPartyAbstract<ID>> RP getRelatedParty(USER user) {
        RelatedPartyAbstract<ID> relatedParty = new RelatedPartyAbstract<>() {
        };
        relatedParty.setRelatedId(user.getId());
        relatedParty.setName(user.getName());
        return (RP) relatedParty;
    }
}
