package se.iths.meritwos.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

import static io.restassured.RestAssured.baseURI;

abstract class BaseTest {
    @Container
    private static MySQLContainer dbContainer = (MySQLContainer) new MySQLContainer("mysql:8.0.32");
    static final ObjectMapper objectMapper = new ObjectMapper();

    @DynamicPropertySource
    public static void overRideProperty(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", dbContainer::getJdbcUrl);
        registry.add("spring.datasource.username", dbContainer::getUsername);
        registry.add("spring.datasource.password", dbContainer::getPassword);
    }

    @BeforeAll
    public static void setUp() {
        baseURI = "http://localhost:8080";

    }
}
