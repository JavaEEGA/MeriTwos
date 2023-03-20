package se.iths.meritwos.integrationTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.iths.meritwos.user.User;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationUserTest extends BaseTest {


    User user = new User(1L, "Oliver", "12345", User.Role.Admin);
    User user2 = new User(2L, "William", "2345", User.Role.Student);

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
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/users/1")
                .then()
                .extract()
                .response();
    }
}
