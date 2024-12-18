package ir.msob.jima.crud.commons;

import ir.msob.jima.core.commons.domain.SampleCriteria;
import ir.msob.jima.crud.commons.common.ConcreteBaseDomainCrudRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BaseDomainCrudRepositoryTest {

    @Test
    void testGetCriteriaClass() {
        // Create a mock implementation of BaseDomainCrudRepository
        ConcreteBaseDomainCrudRepository repository = new ConcreteBaseDomainCrudRepository();

        // Call the getCriteriaClass method
        Class<SampleCriteria<String>> criteriaClass = repository.getCriteriaClass();

        // Perform an assertion to check if the returned class matches our expectation
        assertEquals(SampleCriteria.class, criteriaClass);
    }

}
