package ir.msob.jima.crud.service;

import ir.msob.jima.core.commons.criteria.SampleCriteria;
import ir.msob.jima.core.commons.domain.SampleDomain;
import ir.msob.jima.core.commons.dto.SampleDto;
import ir.msob.jima.crud.service.common.ConcreteParentDomainCrudService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParentDomainCrudServiceTest {

    ConcreteParentDomainCrudService service = new ConcreteParentDomainCrudService();

    private static SampleDomain<String> prepareDomain(String id) {
        SampleDomain<String> domain1 = new SampleDomain<>();
        domain1.setId(id);
        return domain1;
    }

    @Test
    void testGetDtoClass() {
        // Call the getDtoClass method
        Class<SampleDto<String>> dtoClass = service.getDtoClass();

        // Perform an assertion to check if the returned class matches our expectation
        assertEquals(SampleDto.class, dtoClass);
    }

    @Test
    void testGetCriteriaClass() {
        // Call the getCriteriaClass method
        Class<SampleCriteria<String>> criteriaClass = service.getCriteriaClass();

        // Perform an assertion to check if the returned class matches our expectation
        assertEquals(SampleCriteria.class, criteriaClass);
    }

    @Test
    void testNewCriteriaClass() throws Exception {

        // Call the newCriteriaClass method
        SampleCriteria<String> criteria = service.newCriteriaClass();

        // Perform an assertion to check if the returned instance is not null
        assertEquals(SampleCriteria.class, criteria.getClass());
    }

    @Test
    void testPrepareIds() {
        // Create a collection of domain entities for testing
        Collection<SampleDomain<String>> domains = Arrays.asList(prepareDomain("1"), prepareDomain("2"), prepareDomain("3"));

        // Call the prepareIds method
        Collection<String> ids = service.prepareIds(domains);

        // Perform assertions to check if the collection of IDs matches our expectations
        assertEquals(3, ids.size());
        assertTrue(ids.contains("1"));
        assertTrue(ids.contains("2"));
        assertTrue(ids.contains("3"));
    }
}
