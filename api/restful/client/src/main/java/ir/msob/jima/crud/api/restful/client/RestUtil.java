package ir.msob.jima.crud.api.restful.client;

import ir.msob.jima.core.commons.domain.DomainInfo;
import ir.msob.jima.core.commons.domain.DtoInfo;

/**
 * Utility class for generating RESTful URIs for CRUD operations
 * based on {@link DomainInfo} and {@link DtoInfo}.
 * <p>
 * This class is part of the MSOB Framework and provides static helper methods
 * for constructing standardized API endpoints.
 * </p>
 * <p>
 * Instances of this class cannot be created as it contains only static methods.
 * </p>
 */
public class RestUtil {

    // Private constructor to prevent instantiation
    private RestUtil() {
    }

    /**
     * Generates a RESTful URI for a given combination of DTO and domain.
     * <p>
     * The returned URI follows the format: {@code /api/{version}/{domainName}}.
     * </p>
     *
     * @param dtoInfo    the {@link DtoInfo} containing the version information; must not be null
     * @param domainInfo the {@link DomainInfo} containing the domain name; must not be null
     * @return a formatted URI string
     * @throws IllegalArgumentException if either {@code dtoInfo} or {@code domainInfo} is null
     */
    public static String uri(DtoInfo dtoInfo, DomainInfo domainInfo) {
        if (dtoInfo == null || domainInfo == null) {
            throw new IllegalArgumentException("dtoInfo and domainInfo must not be null");
        }
        return String.format("/api/%s/%s", dtoInfo.version(), domainInfo.domainName());
    }
}
