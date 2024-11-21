package ir.msob.jima.crud.api.restful;

import ir.msob.jima.core.commons.domain.DomainService;

/**
 * The RestUtil class provides utility methods for generating RESTful URIs based on a DomainService.
 * It is designed to work with the MSOB Framework for CRUD operations.
 *
 * <p>Instances of this class cannot be created as it consists of only static utility methods.
 */
public class RestUtil {
    private RestUtil() {
    }

    /**
     * Generates a RESTful URI for a given DomainService.
     *
     * @param domainService The DomainService for which the URI is generated.
     * @return A formatted URI string in the "/api/{version}/{domainUri}" format.
     */
    public static String uri(DomainService domainService) {
        return String.format("/api/%s/%s", domainService.version(), domainService.domainName());
    }
}
