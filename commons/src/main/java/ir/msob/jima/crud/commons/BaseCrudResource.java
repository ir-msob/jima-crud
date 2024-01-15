package ir.msob.jima.crud.commons;

import ir.msob.jima.core.commons.exception.resourcenotfound.ResourceNotFoundException;
import ir.msob.jima.core.commons.model.operation.ConditionalOnOperationUtil;

/**
 * The {@code BaseCrudResource} interface defines common functionality for resources that are part of the CRUD (Create, Read, Update, Delete) operations.
 * It includes a default method for CRUD validation, ensuring that the specified CRUD operation is allowed for the implementing class.
 * This interface uses the {@code ConditionalOnOperationUtil} class to check if the given CRUD operation is supported by the implementing class.
 * If the operation is not supported, a {@code ResourceNotFoundException} is thrown with an appropriate error message.
 *
 * @author Yaqub Abdi
 */
public interface BaseCrudResource {

    /**
     * Performs CRUD validation to ensure that the specified CRUD operation is allowed for the implementing class.
     *
     * @param operation The CRUD operation to validate.
     * @throws ResourceNotFoundException If the specified CRUD operation is not supported by the implementing class.
     */
    default void crudValidation(String operation) {
        if (!ConditionalOnOperationUtil.hasOperation(operation, getClass()))
            throw new ResourceNotFoundException("Unable to find resource", operation);
    }
}
