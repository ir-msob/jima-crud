package ir.msob.jima.crud.reactive.service;

import ir.msob.jima.crud.reactive.ConcreteParentDomainCrudService;
import ir.msob.jima.platform.api.domain.criteria.SampleDomainCriteria;
import ir.msob.jima.platform.api.domain.dto.SampleDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParentDomainCrudServiceTest {

    ConcreteParentDomainCrudService service = new ConcreteParentDomainCrudService();

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
        Class<SampleDomainCriteria<String>> criteriaClass = service.getCriteriaClass();

        // Perform an assertion to check if the returned class matches our expectation
        assertEquals(SampleDomainCriteria.class, criteriaClass);
    }

    @Test
    void testNewCriteriaClass() {

        // Call the newCriteriaClass method
        SampleDomainCriteria<String> criteria = service.newCriteriaClass();

        // Perform an assertion to check if the returned instance is not null
        assertEquals(SampleDomainCriteria.class, criteria.getClass());
    }
}
