package ir.msob.jima.crud.api.kafka.service.domain.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.beans.properties.JimaProperties;
import ir.msob.jima.core.commons.relatedobject.relatedparty.RelatedPartyAbstract;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.ral.mongo.it.security.ProjectUser;
import ir.msob.jima.security.it.BaseSecurityProjectUserService;
import ir.msob.jima.security.ral.keycloak.it.security.BaseKeycloakProjectUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;

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
