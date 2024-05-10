package ir.msob.jima.crud.sample.restful.domain;

import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import ir.msob.jima.crud.sample.restful.base.CrudDataProvider;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static ir.msob.jima.core.test.CoreTestData.DEFAULT_STRING;
import static ir.msob.jima.core.test.CoreTestData.UPDATED_STRING;


/**
 * This class provides test data for the {@link SampleDomain} class. It extends the {@link CrudDataProvider} class
 * and provides methods to create new test data objects, update existing data objects, and generate JSON patches for updates.
 */
@Component
public class SampleDomainDataProvider extends CrudDataProvider<SampleDomain, SampleDto, SampleCriteria, SampleRepository, SampleService> {

    private static SampleDto newDto;
    private static SampleDto newMandatoryDto;

    /**
     * Creates a new DTO object with default values.
     */
    public static void createNewDto() {
        newDto = prepareMandatoryDto();
        newDto.setDomainField(DEFAULT_STRING);
    }

    /**
     * Creates a new DTO object with mandatory fields set.
     */
    public static void createMandatoryNewDto() {
        newMandatoryDto = prepareMandatoryDto();
    }

    /**
     * Creates a new DTO object with mandatory fields set.
     */
    public static SampleDto prepareMandatoryDto() {
        SampleDto sampleDto = new SampleDto();
        sampleDto.setDomainMandatoryField(DEFAULT_STRING);
        return sampleDto;
    }

    /**
     * @throws JsonPointerException if there is an error creating the JSON patch.
     * @inheritDoc
     */
    @Override
    @SneakyThrows
    public JsonPatch getJsonPatch() {
        List<JsonPatchOperation> operations = getMandatoryJsonPatchOperation();
        operations.add(new ReplaceOperation(new JsonPointer(String.format("/%s", SampleDomain.FN.domainField)), new TextNode(UPDATED_STRING)));
        return new JsonPatch(operations);
    }

    /**
     * @throws JsonPointerException if there is an error creating the JSON patch.
     * @inheritDoc
     */
    @Override
    @SneakyThrows
    public JsonPatch getMandatoryJsonPatch() {
        return new JsonPatch(getMandatoryJsonPatchOperation());
    }

    /**
     * @inheritDoc
     */
    @Override
    public SampleDto getNewDto() {
        return newDto;
    }

    /**
     * Updates the given DTO object with the updated value for the domain field.
     *
     * @param dto the DTO object to update
     */
    @Override
    public void getUpdateDto(SampleDto dto) {
        dto.setDomainField(UPDATED_STRING);
    }

    /**
     * @inheritDoc
     */
    @Override
    public SampleDto getMandatoryNewDto() {
        return newMandatoryDto;
    }

    /**
     * Updates the given DTO object with the updated value for the mandatory field.
     *
     * @param dto the DTO object to update
     */
    @Override
    public void getMandatoryUpdateDto(SampleDto dto) {
        dto.setDomainField(UPDATED_STRING);
    }

    /**
     * Creates a list of JSON patch operations for updating the mandatory field.
     *
     * @return a list of JSON patch operations
     * @throws JsonPointerException if there is an error creating the JSON pointer.
     */
    public List<JsonPatchOperation> getMandatoryJsonPatchOperation() throws JsonPointerException {
        List<JsonPatchOperation> operations = new ArrayList<>();
        operations.add(new ReplaceOperation(new JsonPointer(String.format("/%s", SampleDomain.FN.domainMandatoryField)), new TextNode(UPDATED_STRING)));
        return operations;
    }
}