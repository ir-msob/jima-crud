package ir.msob.jima.crud.ral.hr.it.domain;

import ir.msob.jima.core.ral.hr.it.test.TestDomain;
import ir.msob.jima.crud.ral.hr.it.TestApplication;
import ir.msob.jima.crud.ral.hr.it.TestBeanConfiguration;
import ir.msob.jima.crud.ral.hr.it.test.TestRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Additional integration tests focusing on entity relations:
 * - @Embedded (AddressInfo)
 * - @ManyToOne (Owner)
 * - @OneToMany (Child list)
 * - @ManyToMany (Tag set)
 * <p>
 * NOTE: These tests assume that the related classes (Owner, Child, Tag, AddressInfo)
 * are accessible from this test package (i.e. are public). If they're package-private
 * you need to make them public so the test can build instances of them.
 */
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
        registry.add("spring.r2dbc.url", () ->
                String.format("r2dbc:postgresql://%s:%d/%s",
                        POSTGRESQL_CONTAINER.getHost(),
                        POSTGRESQL_CONTAINER.getMappedPort(5432),
                        POSTGRESQL_CONTAINER.getDatabaseName()
                )
        );
        registry.add("spring.r2dbc.username", POSTGRESQL_CONTAINER::getUsername);
        registry.add("spring.r2dbc.password", POSTGRESQL_CONTAINER::getPassword);

        // Hibernate/JPA properties can be left if needed for other beans
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    private TestDomain newBaseDomain() {
        TestDomain d = new TestDomain();
        d.setDomainField("VAL_");
        return d;
    }

    @BeforeEach
    void cleanDatabase() {
        testRepository.removeAll().collectList().block();
    }

    @Test
    @Order(1)
    void testEmbeddedAddressPersisted() {
        TestDomain domain = newBaseDomain();

        // create and set embedded address (make sure AddressInfo is public)
        TestDomain.AddressInfo addr = new TestDomain.AddressInfo();
        addr.setStreet("Main St");
        addr.setCity("Brussels");
        addr.setZip("1000");
        domain.setAddress(addr);

        TestDomain saved = testRepository.insertOne(domain).block();
        assertNotNull(saved);

        TestDomain fetched = testRepository.findById(saved.getId()).block();
        assertNotNull(fetched);
        assertNotNull(fetched.getAddress(), "Embedded address should not be null after fetch");
        assertEquals("Main St", fetched.getAddress().getStreet());
        assertEquals("Brussels", fetched.getAddress().getCity());
        assertEquals("1000", fetched.getAddress().getZip());
    }

    @Test
    @Order(2)
    void testManyToOneOwnerCascadePersist() {
        TestDomain domain = newBaseDomain();

        // create and set owner (make sure Owner is public)
        TestDomain.Owner owner = new TestDomain.Owner();
        owner.setName("Owner A");
        domain.setOwner(owner);

        TestDomain saved = testRepository.insertOne(domain).block();
        assertNotNull(saved);

        TestDomain fetched = testRepository.findById(saved.getId()).block();
        assertNotNull(fetched);
        assertNotNull(fetched.getOwner(), "Owner should be present after fetch");
        assertEquals("Owner A", fetched.getOwner().getName());
    }

    @Test
    @Order(3)
    void testOneToManyChildrenCascadePersist() {
        TestDomain domain = newBaseDomain();

        // create children (make sure Child is public)
        TestDomain.Child c1 = new TestDomain.Child();
        c1.setName("child-1");
        // parent set on child side to maintain bidirectional relation
        c1.setParent(domain);

        TestDomain.Child c2 = new TestDomain.Child();
        c2.setName("child-2");
        c2.setParent(domain);

        domain.getChildren().add(c1);
        domain.getChildren().add(c2);

        TestDomain saved = testRepository.insertOne(domain).block();
        assertNotNull(saved);

        TestDomain fetched = testRepository.findById(saved.getId()).block();
        assertNotNull(fetched);
        assertNotNull(fetched.getChildren());
        assertEquals(2, fetched.getChildren().size(), "Should have persisted 2 children");

        // verify child names
        Set<String> names = new HashSet<>();
        for (TestDomain.Child ch : fetched.getChildren()) names.add(ch.getName());
        assertTrue(names.contains("child-1") && names.contains("child-2"));
    }

    @Test
    @Order(4)
    void testManyToManyTagsPersistAndJoinTable() {
        TestDomain domain = newBaseDomain();

        TestDomain.Tag t1 = new TestDomain.Tag();
        t1.setName("tag-a");
        TestDomain.Tag t2 = new TestDomain.Tag();
        t2.setName("tag-b");

        domain.getTags().add(t1);
        domain.getTags().add(t2);

        TestDomain saved = testRepository.insertOne(domain).block();
        assertNotNull(saved);

        TestDomain fetched = testRepository.findById(saved.getId()).block();
        assertNotNull(fetched);
        assertNotNull(fetched.getTags());
        assertEquals(2, fetched.getTags().size(), "Should have 2 tags via join table");

        Set<String> tagNames = new HashSet<>();
        for (TestDomain.Tag tag : fetched.getTags()) tagNames.add(tag.getName());
        assertTrue(tagNames.contains("tag-a") && tagNames.contains("tag-b"));
    }

    @Test
    @Order(5)
    void testOrphanRemovalOnChildren() {
        // insert domain with two children
        TestDomain domain = newBaseDomain();
        TestDomain.Child c1 = new TestDomain.Child();
        c1.setName("orphan-1");
        c1.setParent(domain);
        TestDomain.Child c2 = new TestDomain.Child();
        c2.setName("orphan-2");
        c2.setParent(domain);
        domain.getChildren().add(c1);
        domain.getChildren().add(c2);

        TestDomain saved = testRepository.insertOne(domain).block();
        assertNotNull(saved);

        // remove one child and update domain
        saved.getChildren().removeIf(ch -> "orphan-1".equals(ch.getName()));
        TestDomain updated = testRepository.updateOne(saved).block();
        assertNotNull(updated);

        TestDomain fetched = testRepository.findById(saved.getId()).block();
        assertNotNull(fetched);
        assertEquals(1, fetched.getChildren().size(), "Orphaned child should have been removed");

        // remaining child's name should be orphan-2
        assertEquals("orphan-2", fetched.getChildren().get(0).getName());
    }

    // small sanity test re-using repository operations
    @Test
    @Order(6)
    void testCountAfterRelationOperations() {
        // ensure repository count reflects inserted domains
        TestDomain d1 = newBaseDomain();
        TestDomain d2 = newBaseDomain();
        testRepository.insertOne(d1).block();
        testRepository.insertOne(d2).block();

        Long count = testRepository.countAll().block();
        assertEquals(2L, count);
    }
}
