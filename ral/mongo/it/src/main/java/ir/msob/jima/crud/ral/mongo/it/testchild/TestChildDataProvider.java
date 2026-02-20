package ir.msob.jima.crud.ral.mongo.it.testchild;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import ir.msob.jima.core.commons.id.BaseIdService;
import ir.msob.jima.core.ral.mongo.it.test.TestDomain;
import ir.msob.jima.core.ral.mongo.it.test.TestDto;
import ir.msob.jima.core.ral.mongo.it.testchild.TestChildCriteria;
import ir.msob.jima.core.ral.mongo.it.testchild.TestChildDomain;
import ir.msob.jima.core.ral.mongo.it.testchild.TestChildDto;
import ir.msob.jima.crud.ral.mongo.it.base.ChildDomainCrudDataProvider;
import ir.msob.jima.crud.ral.mongo.it.base.DomainCrudDataProvider;
import ir.msob.jima.crud.ral.mongo.it.test.TestDataProvider;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static ir.msob.jima.core.test.CoreTestData.DEFAULT_STRING;
import static ir.msob.jima.core.test.CoreTestData.UPDATED_STRING;

/**
 * This class provides test data for the {@link TestDomain} class. It extends the {@link DomainCrudDataProvider} class
 * and provides methods to create new test data objects, update existing data objects, and generate JSON patches for updates.
 */
@Component
public class TestChildDataProvider extends ChildDomainCrudDataProvider<TestChildDomain, TestChildDto, TestChildCriteria, TestChildRepository, TestChildServiceDomain> {

    private static TestChildDto newDto;
    private static TestChildDto newMandatoryDto;

    public TestChildDataProvider(BaseIdService idService, ObjectMapper objectMapper, TestChildServiceDomain service) throws ExecutionException, InterruptedException {
        super(idService, objectMapper, service);
    }

    /**
     * Creates a new DTO object with default values.
     */
    public static void createNewDto() {
        newDto = new TestChildDto();
        newDto.setDomainField(DEFAULT_STRING);
    }

    /**
     * Creates a new DTO object with mandatory fields set.
     */
    public static void createMandatoryNewDto() {
        newMandatoryDto = new TestChildDto();
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
    public TestChildDto getNewDto() {
        return newDto;
    }

    /**
     * Updates the given DTO object with the updated value for the domain field.
     *
     * @param dto the DTO object to update
     */
    @Override
    public void updateDto(TestChildDto dto) {
        dto.setDomainField(UPDATED_STRING);
    }

    /**
     *
     */
    @Override
    public TestChildDto getMandatoryNewDto() {
        return newMandatoryDto;
    }

    /**
     * Updates the given DTO object with the updated value for the mandatory field.
     *
     * @param dto the DTO object to update
     */
    @Override
    public void updateMandatoryDto(TestChildDto dto) {
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