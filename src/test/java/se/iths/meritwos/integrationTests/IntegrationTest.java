package se.iths.meritwos.integrationTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import se.iths.meritwos.ad.Ad;
import se.iths.meritwos.user.User;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTest extends BaseTest {


    User user = new User("Oliver", "12345", "ADMIN");
    User user2 = new User("William", "2345", "ADMIN");


    @Test
    @Order(1)
    void addUserToDBShouldReturnOk() throws JsonProcessingException {
        var response = given()
                .auth()
                .basic("admin", "admin")
                .header("Content-type", "application/json")
                .and()
                .body(objectMapper.writeValueAsString(user))
                .when()
                .post("/api/users/register")
                .then().extract().response();

        assertThat(response.statusCode()).isEqualTo(201);
    }

    @Test
    @Order(2)
    void getAllUsersFromDBShouldReturnUser() {

        var response = given()
                .auth().basic("admin", "admin")
                .contentType(ContentType.JSON)
                .when()
                .get("/api/users")
                .then()
                .extract()
                .response();

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getString("name")).isEqualTo("[admin, " + user.getName() + "]");
        assertThat(response.jsonPath().getString("role")).isEqualTo("[[ADMIN], " + user.getRole() + "]");


    }

    @Test
    @Order(3)
    void putShouldReturnUpdatedUser() throws JsonProcessingException {
        var updateUser = given()
                .auth().basic("admin", "admin")
                .header("Content-type", "application/json")
                .and()
                .body(objectMapper.writeValueAsString(user2))
                .put("/api/users/Oliver")
                .then()
                .extract().response();


        assertThat(updateUser.statusCode()).isEqualTo(200);
        assertThat(getGetUser().jsonPath().getString("name")).isEqualTo(user2.getName());
    }

    @Test
    @Order(4)
    void deleteUserWithInvalidUserShouldReturn401() {
        var deleteUser = given()
                .delete("/api/users/William")
                .then()
                .extract().response();
        assertThat(deleteUser.statusCode()).isEqualTo(401);


    }

    @Test
    @Order(5)
    void addUserThatAlreadyExistsShouldReturnConflict() throws JsonProcessingException {
        var response = given()
                .auth().basic("admin", "admin")
                .header("Content-type", "application/json")
                .and()
                .body(objectMapper.writeValueAsString(user2))
                .when()
                .post("/api/users/register")
                .then().extract().response();

        assertThat(response.statusCode()).isEqualTo(409);
    }

    @Test
    @Order(6)
    void deleteUserShouldReturnEmptyArray() {
        var deleteUser = given()
                .auth().basic("admin", "admin")
                .delete("/api/users/William")
                .then()
                .extract().response();
        assertThat(deleteUser.statusCode()).isEqualTo(200);
        assertThat(getGetUser().statusCode()).isEqualTo(404);

    }

    @Test
    void getUserWithInvalidUserShouldReturn401() {
        var response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/users/William")
                .then()
                .extract()
                .response();
        assertThat(response.statusCode()).isEqualTo(401);
    }

    @Test
    void addUserWithInvalidUserToDBShouldReturn401() throws JsonProcessingException {
        var response = given()
                .header("Content-type", "application/json")
                .and()
                .body(objectMapper.writeValueAsString(user))
                .when()
                .post("/api/users/register")
                .then().extract().response();

        assertThat(response.statusCode()).isEqualTo(401);
    }

    private static Response getGetUser() {
        return given()
                .auth().basic("admin", "admin")
                .contentType(ContentType.JSON)
                .when()
                .get("/api/users/William")
                .then()
                .extract()
                .response();
    }

    @Test
    void adsWithoutLoginReturnsOK() {
        var response = given()
                .get("/ads")
                .then().extract().response();

        assertThat(response.statusCode()).isEqualTo(200);

    }

    @Test
    void newStudentWithLoginReturnsOk() {
        var response = given()
                .auth().basic("admin", "admin")
                .get("/newstudent")
                .then().extract().response();

        assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    void newAdWithLoginReturnsOk() {
        var response = given()
                .auth().basic("admin", "admin")
                .get("/newad")
                .then().extract().response();

        assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    void newCompanyWithLoginReturnsOk() {
        var response = given()
                .auth().basic("admin", "admin")
                .get("/newcompany")
                .then().extract().response();

        assertThat(response.statusCode()).isEqualTo(200);
    }

//    @Test
//    void addNewAdCreatesNewAddInDatabase() throws JsonProcessingException {
//        var postResponse = given()
//                .auth().basic("admin", "admin")
//                .body(objectMapper.writeValueAsString(new Ad("Test", "test")))
//                .post("/api/companies")
//                .then().extract().response();
//
//        var getResponse = given()
//                .auth().basic("admin", "admin")
//                .get("/api/companies")
//                .then().extract().response();
//
//        assertThat(postResponse.statusCode()).isEqualTo(200);
//
//
//
//
//
//
//
//    }



//        @Test
//    void newStudentWithoutLoginReturns302() {
//        var response = given()
//                .get("/newstudent")
//                .then()
//                .log().ifStatusCodeIsEqualTo(302);
//
//        assertThat(response.log()).isEqualTo(301);
//    }


}
