package ir.msob.jima.crud.commons;

import ir.msob.jima.core.commons.criteria.SampleCriteria;
import ir.msob.jima.crud.commons.common.ConcreteBaseCrudRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BaseCrudRepositoryTest {

    @Test
    void testGetCriteriaClass() {
        // Create a mock implementation of BaseCrudRepository
        ConcreteBaseCrudRepository repository = new ConcreteBaseCrudRepository();

        // Call the getCriteriaClass method
        Class<SampleCriteria<String>> criteriaClass = repository.getCriteriaClass();

        // Perform an assertion to check if the returned class matches our expectation
        assertEquals(SampleCriteria.class, criteriaClass);
    }

}
