package ir.msob.jima.crud.ral.jpa.it.domain;

import ir.msob.jima.core.ral.jpa.commons.query.JpaQuery;
import ir.msob.jima.core.ral.jpa.it.test.TestDomain;
import ir.msob.jima.crud.ral.jpa.it.TestApplication;
import ir.msob.jima.crud.ral.jpa.it.TestBeanConfiguration;
import ir.msob.jima.crud.ral.jpa.it.test.TestRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

@SpringBootTest(classes = {TestApplication.class, TestBeanConfiguration.class})
@Testcontainers
@CommonsLog
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestRepositoryIt {

    static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:17-alpine"))
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");

    static {
        POSTGRESQL_CONTAINER.start();
    }

    @Autowired
    private TestRepository testRepository;

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        // Use JDBC datasource properties for JPA / Hibernate
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");

        // let Hibernate create/drop schema for tests
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    private TestDomain newDomain() {
        TestDomain d = new TestDomain();
        d.setDomainField("VALUE_");
        return d;
    }

    @BeforeEach
    void cleanDatabase() {
        // removeAll() is implemented on your reactive-wrapped JPA repo
        testRepository.removeAll().collectList().block();
    }

    @Test
    @Order(1)
    void testInsertAndFindById() {
        TestDomain domain = newDomain();
        TestDomain saved = testRepository.insertOne(domain).block();
        Assertions.assertNotNull(saved);

        TestDomain fetched = testRepository.findById(saved.getId()).block();
        Assertions.assertNotNull(fetched);
        Assertions.assertEquals(saved.getDomainField(), fetched.getDomainField());
    }

    @Test
    @Order(2)
    void testUpdateOne() {
        TestDomain domain = newDomain();
        TestDomain saved = testRepository.insertOne(domain).block();

        saved.setDomainField("UPDATED_FIELD");
        TestDomain updated = testRepository.updateOne(saved).block();

        Assertions.assertEquals("UPDATED_FIELD", updated.getDomainField());
    }

    @Test
    @Order(3)
    void testGetOne() {
        TestDomain saved = testRepository.insertOne(newDomain()).block();

        Specification<TestDomain> specById = (root, cq, cb) -> cb.equal(root.get("id"), saved.getId());

        JpaQuery<TestDomain> query = new JpaQuery<TestDomain>()
                .where((Specification) specById);

        TestDomain found = testRepository.getOne(query).block();
        Assertions.assertNotNull(found);
        Assertions.assertEquals(saved.getId(), found.getId());
    }

    @Test
    @Order(4)
    void testGetMany() {
        testRepository.insertOne(newDomain()).block();
        testRepository.insertOne(newDomain()).block();

        JpaQuery<TestDomain> q = new JpaQuery<>();

        List<TestDomain> list = testRepository.getMany(q).collectList().block();
        Assertions.assertTrue(list.size() >= 2);
    }

    @Test
    @Order(5)
    void testGetPage() {
        for (int i = 0; i < 5; i++) testRepository.insertOne(newDomain()).block();

        JpaQuery<TestDomain> q = new JpaQuery<TestDomain>()
                .with(PageRequest.of(0, 2, Sort.by("id")));

        var page = testRepository.getPage(q, PageRequest.of(0, 2)).block();

        Assertions.assertNotNull(page);
        Assertions.assertEquals(2, page.getContent().size());
        Assertions.assertTrue(page.getTotalElements() >= 5L);
    }

    @Test
    @Order(6)
    void testRemoveOne() {
        TestDomain d = testRepository.insertOne(newDomain()).block();

        Specification<TestDomain> specById = (root, cq, cb) -> cb.equal(root.get("id"), d.getId());

        JpaQuery<TestDomain> q = new JpaQuery<TestDomain>()
                .where((Specification) specById);

        TestDomain removed = testRepository.removeOne(q).block();
        Assertions.assertNotNull(removed);

        TestDomain after = testRepository.findById(d.getId()).block();
        Assertions.assertNull(after);
    }

    @Test
    @Order(7)
    void testRemoveMany() {
        testRepository.insertOne(newDomain()).block();
        testRepository.insertOne(newDomain()).block();

        JpaQuery<TestDomain> q = new JpaQuery<>(); // ALL

        List<TestDomain> removed = testRepository.removeMany(q).collectList().block();
        Assertions.assertTrue(removed.size() >= 2);

        Long count = testRepository.countAll().block();
        Assertions.assertEquals(0L, count);
    }

    @Test
    @Order(8)
    void testCount() {
        testRepository.insertOne(newDomain()).block();
        testRepository.insertOne(newDomain()).block();

        JpaQuery<TestDomain> q = new JpaQuery<>();
        Long count = testRepository.count(q).block();
        Assertions.assertEquals(2L, count);
    }

    @Test
    @Order(9)
    void testCountAll() {
        testRepository.insertOne(newDomain()).block();
        testRepository.insertOne(newDomain()).block();

        Long count = testRepository.countAll().block();
        Assertions.assertEquals(2L, count);
    }
}
