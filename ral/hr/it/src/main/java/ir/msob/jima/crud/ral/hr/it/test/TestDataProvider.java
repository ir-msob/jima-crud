package ir.msob.jima.crud.ral.hr.it.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import ir.msob.jima.core.commons.id.BaseIdService;
import ir.msob.jima.core.ral.hr.it.test.TestCriteria;
import ir.msob.jima.core.ral.hr.it.test.TestDomain;
import ir.msob.jima.core.ral.hr.it.test.TestDto;
import ir.msob.jima.crud.ral.hr.it.base.DomainCrudDataProvider;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static ir.msob.jima.core.test.CoreTestData.DEFAULT_STRING;
import static ir.msob.jima.core.test.CoreTestData.UPDATED_STRING;

/**
 * This class provides test data for the {@link TestDomain} class. It extends the {@link DomainCrudDataProvider} class
 * and provides methods to create new test data objects, update existing data objects, and generate JSON patches for updates.
 */
@Component
public class TestDataProvider extends DomainCrudDataProvider<TestDomain, TestDto, TestCriteria, TestRepository, TestServiceDomain> {

    private static TestDto newDto;
    private static TestDto newMandatoryDto;

    public TestDataProvider(BaseIdService idService, ObjectMapper objectMapper, TestServiceDomain service) {
        super(idService, objectMapper, service);
    }

    /**
     * Creates a new DTO object with default values.
     */
    public static void createNewDto() {
        newDto = new TestDto();
        newDto.setDomainField(DEFAULT_STRING);
    }

    /**
     * Creates a new DTO object with mandatory fields set.
     */
    public static void createMandatoryNewDto() {
        newMandatoryDto = new TestDto();
        newMandatoryDto.setDomainField(DEFAULT_STRING);
    }


    @Override
    @SneakyThrows
    public JsonPatch getJsonPatch() {
        List<JsonPatchOperation> operations = getMandatoryJsonPatchOperation();
        operations.add(new ReplaceOperation(new JsonPointer(String.format("/%s", TestDomain.FN.domainField)), new TextNode(UPDATED_STRING)));
        return new JsonPatch(operations);
    }

    @Override
    @SneakyThrows
    public JsonPatch getMandatoryJsonPatch() {
        return new JsonPatch(getMandatoryJsonPatchOperation());
    }

    @Override
    public TestDto getNewDto() {
        return newDto;
    }

    /**
     * Updates the given DTO object with the updated value for the domain field.
     *
     * @param dto the DTO object to update
     */
    @Override
    public void updateDto(TestDto dto) {
        dto.setDomainField(UPDATED_STRING);
    }

    /**
     *
     */
    @Override
    public TestDto getMandatoryNewDto() {
        return newMandatoryDto;
    }

    /**
     * Updates the given DTO object with the updated value for the mandatory field.
     *
     * @param dto the DTO object to update
     */
    @Override
    public void updateMandatoryDto(TestDto dto) {
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
        operations.add(new ReplaceOperation(new JsonPointer(String.format("/%s", TestDomain.FN.domainField)), new TextNode(UPDATED_STRING)));
        return operations;
    }
}