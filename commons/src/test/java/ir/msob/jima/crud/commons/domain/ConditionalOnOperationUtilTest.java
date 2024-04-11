package ir.msob.jima.crud.commons.domain;

import ir.msob.jima.core.commons.operation.ConditionalOnOperationUtil;
import ir.msob.jima.core.commons.operation.Operations;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains tests for the DomainCrudUtil class.
 */
class ConditionalOnOperationUtilTest {

    /**
     * Test whether hasOperation returns true when there's no domain annotation.
     */
    @Test
    void testHasOperationNoDomainAnnotation() {
        Class<?> clazz = SomeClassWithoutDomainAnnotation.class;
        // Expect true because the annotation is null.
        assertTrue(ConditionalOnOperationUtil.hasOperation(Operations.GET_ONE, clazz));
        assertTrue(ConditionalOnOperationUtil.hasOperation(Operations.GET_PAGE, clazz));
        assertTrue(ConditionalOnOperationUtil.hasOperation(Operations.GET_MANY, clazz));
        assertTrue(ConditionalOnOperationUtil.hasOperation(Operations.DELETE, clazz));
        assertTrue(ConditionalOnOperationUtil.hasOperation(Operations.DELETE_ALL, clazz));
        assertTrue(ConditionalOnOperationUtil.hasOperation(Operations.COUNT, clazz));
        assertTrue(ConditionalOnOperationUtil.hasOperation(Operations.COUNT_ALL, clazz));
        assertTrue(ConditionalOnOperationUtil.hasOperation(Operations.SAVE, clazz));
        assertTrue(ConditionalOnOperationUtil.hasOperation(Operations.SAVE_MANY, clazz));
        assertTrue(ConditionalOnOperationUtil.hasOperation(Operations.UPDATE, clazz));
        assertTrue(ConditionalOnOperationUtil.hasOperation(Operations.UPDATE_MANY, clazz));
        assertTrue(ConditionalOnOperationUtil.hasOperation(Operations.EDIT, clazz));
        assertTrue(ConditionalOnOperationUtil.hasOperation(Operations.EDIT_MANY, clazz));
    }

    /**
     * Test whether hasOperation returns true for a class that allows the READ operation.
     */
    @Test
    void testHasOperationWithReadOperationAllowed() {
        Class<?> clazz = SomeClassWithReadAllowedAnnotation.class;
        boolean result = ConditionalOnOperationUtil.hasOperation(Operations.GET_ONE, clazz);
        // Expect true because the class allows the READ operation.
        assertTrue(result);
    }

    /**
     * Test whether hasOperation returns true for a class that allows the WRITE operation.
     */
    @Test
    void testHasOperationWithWriteOperationAllowed() {
        Class<?> clazz = SomeClassWithWriteAllowedAnnotation.class;
        boolean result = ConditionalOnOperationUtil.hasOperation(Operations.SAVE, clazz);
        // Expect true because the class allows the WRITE operation.
        assertTrue(result);
    }

    /**
     * Test whether hasOperation returns false for a class that doesn't allow the READ operation.
     */
    @Test
    void testHasOperationWithReadOperationNotAllowed() {
        Class<?> clazz = SomeClassWithWriteAllowedAnnotation.class;
        boolean result = ConditionalOnOperationUtil.hasOperation(Operations.GET_ONE, clazz);
        // Expect false because the class doesn't allow the READ operation.
        assertTrue(result);
    }

    /**
     * Test whether hasOperation returns false for a class that doesn't allow the WRITE operation.
     */
    @Test
    void testHasOperationWithWriteOperationNotAllowed() {
        Class<?> clazz = SomeClassWithReadAllowedAnnotation.class;
        boolean result = ConditionalOnOperationUtil.hasOperation(Operations.SAVE, clazz);
        // Expect false because the class doesn't allow the WRITE operation.
        assertTrue(result);
    }

    /**
     * Test whether hasOperation handles null annotations by returning true.
     */
    @Test
    void testHasOperationWithNullAnnotation() {
        Class<?> clazz = SomeClassWithNullAnnotation.class;
        // Expect true because the annotation is null.
        assertTrue(ConditionalOnOperationUtil.hasOperation(Operations.GET_ONE, clazz));
    }

    private static class SomeClassWithoutDomainAnnotation {
    }

    private static class SomeClassWithReadAllowedAnnotation {
    }

    private static class SomeClassWithWriteAllowedAnnotation {
    }

    private static class SomeClassWithNullAnnotation {

    }
}
