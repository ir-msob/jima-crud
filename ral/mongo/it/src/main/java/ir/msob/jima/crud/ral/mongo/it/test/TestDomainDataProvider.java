package ir.msob.jima.crud.ral.mongo.it.test;

import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import ir.msob.jima.core.ral.mongo.it.test.TestCriteria;
import ir.msob.jima.core.ral.mongo.it.test.TestDomain;
import ir.msob.jima.core.ral.mongo.it.test.TestDto;
import ir.msob.jima.crud.ral.mongo.it.base.CrudDataProvider;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static ir.msob.jima.core.test.CoreTestData.DEFAULT_STRING;
import static ir.msob.jima.core.test.CoreTestData.UPDATED_STRING;

@Component
public class TestDomainDataProvider extends CrudDataProvider<TestDomain, TestDto, TestCriteria, TestRepository, TestService> {


    private static TestDto newDto;
    private static TestDto newMandatoryDto;

    public static void createNewDto() {
        newDto = new TestDto();
        newDto.setDomainField(DEFAULT_STRING);
    }


    public static void createMandatoryNewDto() {
        newMandatoryDto = new TestDto();
        newMandatoryDto.setDomainField(DEFAULT_STRING);
    }


    @SneakyThrows
    @Override
    public JsonPatch getJsonPatch() {
        List<JsonPatchOperation> operations = getMandatoryJsonPatchOperation();
        operations.add(new ReplaceOperation(new JsonPointer(String.format("/%s", TestDomain.FN.domainField)), new TextNode(UPDATED_STRING)));
        return new JsonPatch(operations);
    }

    @SneakyThrows
    @Override
    public JsonPatch getMandatoryJsonPatch() {
        return new JsonPatch(getMandatoryJsonPatchOperation());
    }

    @Override
    public TestDto getNewDto() {
        return newDto;
    }

    @Override
    public void getUpdateDto(TestDto dto) {
        dto.setDomainField(UPDATED_STRING);

    }

    @Override
    public TestDto getMandatoryNewDto() {
        return newMandatoryDto;
    }

    @Override
    public void getMandatoryUpdateDto(TestDto dto) {
        dto.setDomainField(UPDATED_STRING);
    }

    public List<JsonPatchOperation> getMandatoryJsonPatchOperation() throws JsonPointerException {
        List<JsonPatchOperation> operations = new ArrayList<>();
        operations.add(new ReplaceOperation(new JsonPointer(String.format("/%s", TestDomain.FN.domainField)), new TextNode(UPDATED_STRING)));
        return operations;
    }
}
