package ir.msob.jima.crud.api.grpc.test.read;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.crud.api.grpc.commons.CriteriaPageableMsg;
import ir.msob.jima.crud.api.grpc.commons.PageMsg;
import ir.msob.jima.crud.api.grpc.test.ParentCrudGrpcResourceTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.BaseCrudDataProvider;
import ir.msob.jima.crud.test.read.BaseGetPageCrudResourceTest;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * The {@code BaseGetPageCrudGrpcResourceTest} interface represents a set of gRPC-specific test methods for retrieving a page of entities based on a given criteria.
 * It extends both the {@code BaseGetPageCrudResourceTest} and {@code ParentCrudGrpcResourceTest} interfaces, providing gRPC-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to retrieve a page of entities based on a given criteria using gRPC. The result of the retrieval operation is a {@code Page<DTO>}
 * representing the page of entities that match the given criteria.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <Q>    The type of query used for retrieving entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseGetPageCrudResourceTest
 * @see ParentCrudGrpcResourceTest
 */
public interface BaseGetPageCrudGrpcResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseGetPageCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP> {

    /**
     * Executes a gRPC request to retrieve a page of entities based on a given criteria and extracts the result from the response.
     *
     * @param savedDto The data transfer object (DTO) representing the saved entity.
     * @return A page of data transfer objects (DTOs) representing the entities that match the given criteria.
     * @throws DomainNotFoundException If the domain entity is not found.
     * @throws BadRequestException     If the request is malformed or invalid.
     */
    @SneakyThrows
    @Override
    default Page<DTO> getPageRequest(DTO savedDto) throws DomainNotFoundException, BadRequestException {
        // Create an instance of CriteriaPageableMsg with the ID of the saved entity and the page request details
        CriteriaPageableMsg msg = CriteriaPageableMsg.newBuilder()
                .setCriteria(convertToString(CriteriaUtil.idCriteria(getCriteriaClass(), savedDto.getDomainId())))
                .setPageable(convertToString(PageRequest.of(0, 10)))
                .build();
        // Execute the gRPC request with the created CriteriaPageableMsg and extract the result from the response
        PageMsg res = getReactorCrudServiceStub().getPage(Mono.just(msg))
                .toFuture()
                .get();
        // Convert the result to the Page type and return it
        return convertToPage(res.getPage());
    }
}