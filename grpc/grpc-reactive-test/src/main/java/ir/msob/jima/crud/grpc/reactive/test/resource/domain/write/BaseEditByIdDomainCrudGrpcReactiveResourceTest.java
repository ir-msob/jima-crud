package ir.msob.jima.crud.grpc.reactive.test.resource.domain.write;

import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.crud.grpc.reactive.proto.DtoMsg;
import ir.msob.jima.crud.grpc.reactive.proto.IdJsonPatchMsg;
import ir.msob.jima.crud.grpc.reactive.test.resource.domain.ParentDomainCrudGrpcReactiveResourceTest;
import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.test.dataprovider.domain.BaseDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.reactive.test.resource.domain.write.BaseEditByIdDomainCrudReactiveResourceTest;
import ir.msob.jima.crud.test.resource.domain.write.BaseEditByIdDomainCrudResourceTest;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import ir.msob.jima.platform.test.Assertable;
import lombok.SneakyThrows;

import java.io.Serializable;

/**
 * The {@code BaseEditByIdDomainCrudGrpcReactiveResourceTest} interface represents a set of gRPC-specific test methods for editing entities by their ID.
 * It extends both the {@code BaseEditByIdChildDomainCrudReactiveResourceTest} and {@code ParentDomainCrudGrpcReactiveResourceTest} interfaces, providing gRPC-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to edit an entity by its ID using gRPC. The result of the edit operation is a {@code DtoMsg}
 * representing the edited entity.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseEditByIdDomainCrudResourceTest
 * @see ParentDomainCrudGrpcReactiveResourceTest
 */
public interface BaseEditByIdDomainCrudGrpcReactiveResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseEditByIdDomainCrudReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudGrpcReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP> {

    /**
     * Executes a gRPC request to edit an entity by its ID and extracts the result from the response.
     *
     * @param savedDto  The data transfer object (DTO) representing the saved entity.
     * @param jsonPatch The JsonPatch representing the changes to be applied to the entity.
     */
    @SneakyThrows
    @Override
    default void editByIdRequest(DTO savedDto, JsonPatch jsonPatch, Assertable<DTO> assertable) {
        // Create an instance of IdJsonPatchMsg with the ID of the saved entity and the JsonPatch
        IdJsonPatchMsg msg = IdJsonPatchMsg.newBuilder()
                .setId(convertToString(savedDto.getId()))
                .setJsonPatch(convertToString(jsonPatch))
                .build();
        // Execute the gRPC request with the created IdJsonPatchMsg and extract the result from the response
        DtoMsg res = getCrudServiceBlockingStub().editById(msg);
        // Convert the result to the DTO type and return it
        assertable.assertThan(convertToDto(res.getDto()));
    }
}