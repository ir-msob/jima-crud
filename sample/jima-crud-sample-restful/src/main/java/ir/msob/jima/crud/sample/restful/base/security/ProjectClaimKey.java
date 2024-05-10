package ir.msob.jima.crud.sample.restful.base.security;


import ir.msob.jima.core.commons.security.ClaimKey;

/**
 * @author Yaqub Abdi
 */
public class ProjectClaimKey extends ClaimKey {
    public static final String REALM_ACCESS = "realm_access";
    public static final String KEYCLOAK_ROLES = "roles";
    private ProjectClaimKey() {
        super();
    }

}
