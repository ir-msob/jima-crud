package ir.msob.jima.crud.commons;

import ir.msob.jima.core.commons.exception.resourcenotfound.ResourceNotFoundException;
import ir.msob.jima.core.commons.model.operation.ConditionalOnOperationUtil;
import ir.msob.jima.core.commons.model.operation.Operations;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mockStatic;

class BaseCrudResourceTest {

    @Test
    void testCrudValidation_ValidOperation() {
        // Arrange
        BaseCrudResource testResource = new TestCrudResource();
        String validOperation = Operations.READ;

        // Mock the static method hasOperation in ConditionalOnOperationUtil
        try (MockedStatic<ConditionalOnOperationUtil> mockedStatic = mockStatic(ConditionalOnOperationUtil.class)) {
            mockedStatic.when(() -> ConditionalOnOperationUtil.hasOperation(validOperation, TestCrudResource.class)).thenReturn(true);

            // Act
            try {
                testResource.crudValidation(validOperation);
            } catch (ResourceNotFoundException e) {
                // Assert
                fail("Valid operation should not throw ResourceNotFoundException");
            }
        }
    }

    @Test
    void testCrudValidation_InvalidOperation() {
        // Arrange
        BaseCrudResource testResource = new TestCrudResource();
        String invalidOperation = Operations.COUNT;

        // Mock the static method hasOperation in ConditionalOnOperationUtil
        try (MockedStatic<ConditionalOnOperationUtil> mockedStatic = mockStatic(ConditionalOnOperationUtil.class)) {
            mockedStatic.when(() -> ConditionalOnOperationUtil.hasOperation(invalidOperation, TestCrudResource.class)).thenReturn(false);

            assertThrows(
                    ResourceNotFoundException.class,
                    // Act
                    () -> testResource.crudValidation(invalidOperation)
            );
        }
    }

    @Test
    void testCrudValidation_ExceptionMessage() {
        // Arrange
        BaseCrudResource testResource = new TestCrudResource();
        String invalidOperation = Operations.COUNT;

        // Mock the static method hasOperation in ConditionalOnOperationUtil
        try (MockedStatic<ConditionalOnOperationUtil> mockedStatic = mockStatic(ConditionalOnOperationUtil.class)) {
            mockedStatic.when(() -> ConditionalOnOperationUtil.hasOperation(invalidOperation, TestCrudResource.class)).thenReturn(false);

            // Act & Assert
            assertThrows(ResourceNotFoundException.class, () -> testResource.crudValidation(invalidOperation));
        }
    }

    private static class TestCrudResource implements BaseCrudResource {
    }
}
