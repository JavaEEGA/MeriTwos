package se.iths.meritwos.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.function.Supplier;

import static io.restassured.RestAssured.baseURI;
@Testcontainers
abstract class BaseTest {
    @Container
    private static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.0.32");
    @Container
    private static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");
    static final ObjectMapper objectMapper = new ObjectMapper();

    @DynamicPropertySource
    public static void overRideProperty(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.data.mongodb.host", mongoDBContainer::getHost);
        registry.add("spring.data.mongodb.port",mongoDBContainer::getFirstMappedPort);

    }

    @BeforeAll
    public static void setUp() {
        mongoDBContainer.start();
        baseURI = "http://localhost:8080";

    }
}
