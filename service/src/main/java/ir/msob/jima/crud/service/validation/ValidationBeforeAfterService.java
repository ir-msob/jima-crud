package ir.msob.jima.crud.service.validation;

import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.BaseBeforeAfterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Optional;

/**
 * This service class is responsible for executing pre-validation operations before performing "get" and "delete" actions based on a set of criteria.
 * It implements the {@link BaseBeforeAfterService} interface and is designed to be used with CRUD operations.
 */
@Service
@RequiredArgsConstructor
public class ValidationBeforeAfterService implements BaseBeforeAfterService {

    /**
     * Performs pre-validation operations before executing a "get" action based on a set of criteria.
     *
     * @param <ID>     The type of entity IDs.
     * @param <USER>   The type of the user associated with the operation.
     * @param <C>      The type of the criteria used for filtering entities.
     * @param criteria The criteria used for filtering entities.
     * @param user     An optional user associated with the operation.
     * @throws BadRequestException if the validation or initialization process encounters a bad request scenario.
     */
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, C extends BaseCriteria<ID>> void beforeGet(C criteria, Optional<USER> user) throws BadRequestException {
    }

    /**
     * Performs pre-validation operations before executing a "delete" action based on a set of criteria.
     *
     * @param <ID>     The type of entity IDs.
     * @param <USER>   The type of the user associated with the operation.
     * @param <C>      The type of the criteria used for filtering entities.
     * @param criteria The criteria used for filtering entities.
     * @param user     An optional user associated with the operation.
     * @throws BadRequestException if the validation or initialization process encounters a bad request scenario.
     */
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, C extends BaseCriteria<ID>> void beforeDelete(C criteria, Optional<USER> user) throws BadRequestException {
    }
}
