package ir.msob.jima.crud.grpc.reactive.test.resource.domain.read;

import ir.msob.jima.crud.grpc.reactive.proto.CriteriaPageableMsg;
import ir.msob.jima.crud.grpc.reactive.proto.PageMsg;
import ir.msob.jima.crud.grpc.reactive.test.resource.domain.ParentDomainCrudGrpcReactiveResourceTest;
import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.test.dataprovider.domain.BaseDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.reactive.test.resource.domain.read.BaseGetPageDomainCrudReactiveResourceTest;
import ir.msob.jima.crud.test.resource.domain.read.BaseGetPageDomainCrudResourceTest;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestException;
import ir.msob.jima.platform.api.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.api.shared.PageDto;
import ir.msob.jima.platform.api.shared.PageableDto;
import ir.msob.jima.platform.api.util.CriteriaUtil;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import ir.msob.jima.platform.test.Assertable;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;

import java.io.Serializable;

/**
 * The {@code BaseGetPageDomainCrudGrpcReactiveResourceTest} interface represents a set of gRPC-specific test methods for retrieving a page of entities based on a given criteria.
 * It extends both the {@code BaseGetPageChildDomainCrudReactiveResourceTest} and {@code ParentDomainCrudGrpcReactiveResourceTest} interfaces, providing gRPC-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to retrieve a page of entities based on a given criteria using gRPC. The result of the retrieval operation is a {@code Page<DTO>}
 * representing the page of entities that match the given criteria.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseGetPageDomainCrudResourceTest
 * @see ParentDomainCrudGrpcReactiveResourceTest
 */
public interface BaseGetPageDomainCrudGrpcReactiveResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseGetPageDomainCrudReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudGrpcReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP> {

    /**
     * Executes a gRPC request to retrieve a page of entities based on a given criteria and extracts the result from the response.
     *
     * @param savedDto The data transfer object (DTO) representing the saved entity.
     * @throws DomainNotFoundException If the domain entity is not found.
     * @throws BadRequestException     If the request is malformed or invalid.
     */
    @SneakyThrows
    @Override
    default void getPageRequest(DTO savedDto, Assertable<PageDto<DTO>> assertable) throws DomainNotFoundException, BadRequestException {
        // Create an instance of CriteriaPageableMsg with the ID of the saved entity and the page request details
        CriteriaPageableMsg msg = CriteriaPageableMsg.newBuilder()
                .setCriteria(convertToString(CriteriaUtil.idCriteria(getCriteriaClass(), savedDto.getId())))
                .setPageable(convertToString(PageableDto.from(PageRequest.of(0, 10))))
                .build();
        // Execute the gRPC request with the created CriteriaPageableMsg and extract the result from the response
        PageMsg res = getCrudServiceBlockingStub().getPage(msg);
        // Convert the result to the Page type
        assertable.assertThan(convertToPage(res.getPage()));
    }
}