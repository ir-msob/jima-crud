package ir.msob.jima.crud.commons.domain;

import ir.msob.jima.core.commons.operation.ConditionalOnOperationUtil;
import ir.msob.jima.core.commons.properties.CrudProperties;
import ir.msob.jima.core.commons.resource.Resource;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.shared.ResourceType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains tests for the DomainCrudUtil class.
 */
class ConditionalOnOperationUtilTest {

    private final CrudProperties crudProperties = new CrudProperties();

    @Test
    void testHasOperationWithValidDomain() {
        // Set up a domain with allowed operations
        CrudProperties.Domain domain = new CrudProperties.Domain();
        domain.setName("testDomain");
        domain.setOperations(Arrays.asList("save", "delete"));
        crudProperties.getDomains().add(domain);

        // Create a scope for testing
        Scope scope = new Scope() {

            @Override
            public String operation() {
                return "save";
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return Scope.class;
            }
        };

        // Check if the operation is allowed
        assertTrue(ConditionalOnOperationUtil.hasOperation(scope, crudProperties, SomeClassWithDomain.class));
    }

    @Disabled // FIXME
    @Test
    void testHasOperationWithInvalidDomain() {
        // Set up a domain with no allowed operations
        CrudProperties.Domain domain = new CrudProperties.Domain();
        domain.setName("testDomain");
        domain.setOperations(List.of("delete"));
        crudProperties.getDomains().add(domain);

        // Create a scope for testing
        Scope scope = new Scope() {

            @Override
            public String operation() {
                return "save"; // Not allowed
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return Scope.class;
            }
        };

        // Check if the operation is denied
        assertFalse(ConditionalOnOperationUtil.hasOperation(scope, crudProperties, SomeClassWithDomain.class));
    }

    @Test
    void testHasOperationWithNoDomain() {
        // Create a scope for testing
        Scope scope = new Scope() {

            @Override
            public String operation() {
                return "save"; // No domain defined
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return Scope.class;
            }
        };

        // Check if the operation is allowed when no domain is defined
        assertTrue(ConditionalOnOperationUtil.hasOperation(scope, crudProperties, SomeClassWithNoDomain.class));
    }

    private static class SomeClassWithoutDomainAnnotation {
    }

    private static class SomeClassWithReadAllowedAnnotation {
    }

    private static class SomeClassWithWriteAllowedAnnotation {
    }

    private static class SomeClassWithNullAnnotation {

    }

    @Resource(value = "testDomain", type = ResourceType.RESTFUL)
    // Dummy classes for testing
    private static class SomeClassWithDomain {
    }

    private static class SomeClassWithNoDomain {
    }
}
