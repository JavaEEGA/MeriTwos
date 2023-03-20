package se.iths.meritwos.integrationTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.iths.meritwos.user.User;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTests {

    @Container
    private static MySQLContainer dbContainer = (MySQLContainer) new MySQLContainer("mysql:8.0.32");
    private ObjectMapper objectMapper = new ObjectMapper();
    User user = new User(1L, "Oliver", "12345", User.Role.Admin);
    User user2 = new User(2L, "William", "2345", User.Role.Student);

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

    @Test
    @Order(1)
    void addUserToDBShouldReturnOk() throws JsonProcessingException {
        var response = given()
                .header("Content-type", "application/json")
                .and()
                .body(objectMapper.writeValueAsString(user))
                .when()
                .post("/users")
                .then().extract().response();

        assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    @Order(2)
    void getAllUsersFromDBShouldReturnUser() {

        var response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/users")
                .then()
                .extract()
                .response();

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getString("name")).isEqualTo("[" + user.getName() + "]");
        assertThat(response.jsonPath().getString("role")).isEqualTo("[" + user.getRole() + "]");


    }

    @Test
    @Order(3)
    void putShouldReturnUpdatedUser() throws JsonProcessingException {
        var updateUser = given()
                .header("Content-type", "application/json")
                .and()
                .body(objectMapper.writeValueAsString(user2))
                .put("/users/1")
                .then()
                .extract().response();


        assertThat(updateUser.statusCode()).isEqualTo(200);
        assertThat(getGetFirstUser().jsonPath().getString("name")).isEqualTo(user2.getName());
    }

    @Test
    @Order(4)
    void deleteUserShouldReturnEmptyArray() {
        var deleteUser = given()
                .delete("/users/1")
                .then()
                .extract().response();
        assertThat(deleteUser.statusCode()).isEqualTo(200);
        assertThat(getGetFirstUser().statusCode()).isEqualTo(404);

    }

    private static Response getGetFirstUser() {
        var getUser = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/users/1")
                .then()
                .extract()
                .response();
        return getUser;
    }
}
