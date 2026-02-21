package ir.msob.jima.crud.service.domain.operation;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.operation.BaseBeforeAfterDomainOperation;
import ir.msob.jima.core.commons.operation.BaseBeforeAfterOperation;
import ir.msob.jima.core.commons.security.BaseUser;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * This class provides before and after operations for various CRUD operations.
 * It allows you to apply additional logic before and after counting, getting, saving,
 * updating, and deleting records.
 * <p>
 * It uses the strategy pattern to delegate the before and after operations to a collection of
 * BaseBeforeAfterOperation and BaseBeforeAfterDomainOperation instances.
 */
@Service
@RequiredArgsConstructor
public class BeforeAfterOperationComponent {
    /**
     * A collection of BaseBeforeAfterOperation instances.
     */
    private final List<BaseBeforeAfterOperation> beforeAfterOperations;


    /**
     * Executes before counting records based on the provided criteria.
     *
     * @param criteria               The criteria used for counting records.
     * @param user                   A user associated with the operation.
     * @param beforeAfterDomainInfos A collection of BaseBeforeAfterDomainOperation instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> Mono<@NonNull Void> beforeCount(C criteria, USER user, List<BaseBeforeAfterDomainOperation<ID, USER, DTO, C>> beforeAfterDomainInfos) throws DomainNotFoundException, BadRequestException {
        return Flux.concat(
                Flux.fromIterable(
                        beforeAfterOperations == null ? List.of() : beforeAfterOperations
                ).map(op -> op.beforeCount(criteria, user)),

                Flux.fromIterable(
                        beforeAfterDomainInfos == null ? List.of() : beforeAfterDomainInfos
                ).map(op -> op.beforeCount(criteria, user))
        ).then();
    }

    /**
     * Executes after counting records based on the provided criteria.
     *
     * @param criteria               The criteria used for counting records.
     * @param user                   A user associated with the operation.
     * @param beforeAfterDomainInfos A collection of BaseBeforeAfterDomainOperation instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> Mono<@NonNull Void> afterCount(C criteria, USER user, List<BaseBeforeAfterDomainOperation<ID, USER, DTO, C>> beforeAfterDomainInfos) throws DomainNotFoundException, BadRequestException {
        return Flux.concat(
                Flux.fromIterable(
                        beforeAfterOperations == null ? List.of() : beforeAfterOperations
                ).map(op -> op.afterCount(criteria, user)),

                Flux.fromIterable(
                        beforeAfterDomainInfos == null ? List.of() : beforeAfterDomainInfos
                ).map(op -> op.afterCount(criteria, user))
        ).then();
    }

    /**
     * Executes before getting records based on the provided criteria.
     *
     * @param criteria               The criteria used for getting records.
     * @param user                   A user associated with the operation.
     * @param beforeAfterDomainInfos A collection of BaseBeforeAfterDomainOperation instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> Mono<@NonNull Void> beforeGet(C criteria, USER user, List<BaseBeforeAfterDomainOperation<ID, USER, DTO, C>> beforeAfterDomainInfos) throws DomainNotFoundException, BadRequestException {
        return Flux.concat(
                Flux.fromIterable(
                        beforeAfterOperations == null ? List.of() : beforeAfterOperations
                ).map(op -> op.beforeGet(criteria, user)),

                Flux.fromIterable(
                        beforeAfterDomainInfos == null ? List.of() : beforeAfterDomainInfos
                ).map(op -> op.beforeGet(criteria, user))
        ).then();
    }

    /**
     * Executes after getting records based on the provided criteria.
     *
     * @param ids                    The IDs of the retrieved records.
     * @param dtos                   The retrieved DTO objects.
     * @param criteria               The criteria used for getting records.
     * @param user                   A user associated with the operation.
     * @param beforeAfterDomainInfos A collection of BaseBeforeAfterDomainOperation instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> Mono<@NonNull Void> afterGet(Collection<ID> ids, Collection<DTO> dtos, C criteria, USER user, List<BaseBeforeAfterDomainOperation<ID, USER, DTO, C>> beforeAfterDomainInfos) throws DomainNotFoundException, BadRequestException {
        return Flux.concat(
                Flux.fromIterable(
                        beforeAfterOperations == null ? List.of() : beforeAfterOperations
                ).map(op -> op.afterGet(ids, dtos, criteria, user)),

                Flux.fromIterable(
                        beforeAfterDomainInfos == null ? List.of() : beforeAfterDomainInfos
                ).map(op -> op.afterGet(ids, dtos, criteria, user))
        ).then();
    }

    /**
     * Executes before saving records based on the provided DTO.
     *
     * @param dto                    The DTO to be saved.
     * @param user                   A user associated with the operation.
     * @param beforeAfterDomainInfos A collection of BaseBeforeAfterDomainOperation instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> Mono<@NonNull Void> beforeSave(DTO dto, USER user, List<BaseBeforeAfterDomainOperation<ID, USER, DTO, C>> beforeAfterDomainInfos) throws DomainNotFoundException, BadRequestException {
        return Flux.concat(
                Flux.fromIterable(
                        beforeAfterOperations == null ? List.of() : beforeAfterOperations
                ).map(op -> op.beforeSave(dto, user)),

                Flux.fromIterable(
                        beforeAfterDomainInfos == null ? List.of() : beforeAfterDomainInfos
                ).map(op -> op.beforeSave(dto, user))
        ).then();
    }

    /**
     * Executes after saving records based on the provided DTO.
     *
     * @param dto                    The DTO that were saved.
     * @param savedDto               The saved DTO.
     * @param user                   A user associated with the operation.
     * @param beforeAfterDomainInfos A collection of BaseBeforeAfterDomainOperation instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> Mono<@NonNull Void> afterSave(DTO dto, DTO savedDto, USER user, List<BaseBeforeAfterDomainOperation<ID, USER, DTO, C>> beforeAfterDomainInfos) throws DomainNotFoundException, BadRequestException {
        return Flux.concat(
                Flux.fromIterable(
                        beforeAfterOperations == null ? List.of() : beforeAfterOperations
                ).map(op -> op.afterSave(dto, savedDto, user)),

                Flux.fromIterable(
                        beforeAfterDomainInfos == null ? List.of() : beforeAfterDomainInfos
                ).map(op -> op.afterSave(dto, savedDto, user))
        ).then();
    }

    /**
     * Executes before updating records based on the provided DTO.
     *
     * @param previousDto            The previous DTO.
     * @param dto                    The updated DTO.
     * @param user                   A user associated with the operation.
     * @param beforeAfterDomainInfos A collection of BaseBeforeAfterDomainOperation instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> Mono<@NonNull Void> beforeUpdate(DTO previousDto, DTO dto, USER user, List<BaseBeforeAfterDomainOperation<ID, USER, DTO, C>> beforeAfterDomainInfos) throws DomainNotFoundException, BadRequestException {
        return Flux.concat(
                Flux.fromIterable(
                        beforeAfterOperations == null ? List.of() : beforeAfterOperations
                ).map(op -> op.beforeUpdate(previousDto, dto, user)),

                Flux.fromIterable(
                        beforeAfterDomainInfos == null ? List.of() : beforeAfterDomainInfos
                ).map(op -> op.beforeUpdate(previousDto, dto, user))
        ).then();
    }

    /**
     * Executes after updating records based on the provided DTO.
     *
     * @param dto                    The updated DTO.
     * @param updatedDto             The updated DTO after the update.
     * @param user                   A user associated with the operation.
     * @param beforeAfterDomainInfos A collection of BaseBeforeAfterDomainOperation instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> Mono<@NonNull Void> afterUpdate(DTO dto, DTO updatedDto, USER user, List<BaseBeforeAfterDomainOperation<ID, USER, DTO, C>> beforeAfterDomainInfos) throws DomainNotFoundException, BadRequestException {
        return Flux.concat(
                Flux.fromIterable(
                        beforeAfterOperations == null ? List.of() : beforeAfterOperations
                ).map(op -> op.afterUpdate(dto, updatedDto, user)),

                Flux.fromIterable(
                        beforeAfterDomainInfos == null ? List.of() : beforeAfterDomainInfos
                ).map(op -> op.afterUpdate(dto, updatedDto, user))
        ).then();
    }

    /**
     * Executes before deleting records based on the provided criteria.
     *
     * @param criteria               The criteria used for deleting records.
     * @param user                   A user associated with the operation.
     * @param beforeAfterDomainInfos A collection of BaseBeforeAfterDomainOperation instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> Mono<@NonNull Void> beforeDelete(C criteria, USER user, List<BaseBeforeAfterDomainOperation<ID, USER, DTO, C>> beforeAfterDomainInfos) throws DomainNotFoundException, BadRequestException {
        return Flux.concat(
                Flux.fromIterable(
                        beforeAfterOperations == null ? List.of() : beforeAfterOperations
                ).map(op -> op.beforeDelete(criteria, user)),

                Flux.fromIterable(
                        beforeAfterDomainInfos == null ? List.of() : beforeAfterDomainInfos
                ).map(op -> op.beforeDelete(criteria, user))
        ).then();
    }

    /**
     * Executes after deleting records based on the provided criteria.
     *
     * @param dto                    The DTO of the deleted records.
     * @param criteria               The criteria used for deleting records.
     * @param dtoClass               The class of DTO used.
     * @param user                   A user associated with the operation.
     * @param beforeAfterDomainInfos A collection of BaseBeforeAfterDomainOperation instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> Mono<@NonNull Void> afterDelete(DTO dto, C criteria, Class<DTO> dtoClass, USER user, List<BaseBeforeAfterDomainOperation<ID, USER, DTO, C>> beforeAfterDomainInfos) throws DomainNotFoundException, BadRequestException {
        return Flux.concat(
                Flux.fromIterable(
                        beforeAfterOperations == null ? List.of() : beforeAfterOperations
                ).map(op -> op.afterDelete(dto, criteria, user)),

                Flux.fromIterable(
                        beforeAfterDomainInfos == null ? List.of() : beforeAfterDomainInfos
                ).map(op -> op.afterDelete(dto, criteria, user))
        ).then();
    }
}