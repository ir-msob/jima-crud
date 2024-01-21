package ir.msob.jima.crud.commons;

/**
 * The {@code BaseCrudResource} interface defines common functionality for resources that are part of the CRUD (Create, Read, Update, Delete) operations.
 * It includes a default method for CRUD validation, ensuring that the specified CRUD operation is allowed for the implementing class.
 * This interface uses the {@code ConditionalOnOperationUtil} class to check if the given CRUD operation is supported by the implementing class.
 * If the operation is not supported, a {@code ResourceNotFoundException} is thrown with an appropriate error message.
 *
 * @author Yaqub Abdi
 */
public interface BaseCrudResource {

}
