//package se.iths.meritwos.integrationTests;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.test.context.support.WithMockUser;
//
//import static io.restassured.RestAssured.given;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//public class IntegrationWebControllerTest extends BaseTest {
//
//
//    @Test
//    void adsWithoutLoginReturnsOK() {
//        var response = given()
//                .get("/ads")
//                .then().extract().response();
//
//        assertThat(response.statusCode()).isEqualTo(200);
//
//    }
//
//
////    @Test
////    void newStudentWithoutLoginReturns302() {
////        var response = given()
////                .get("/newstudent")
////                .then()
////                .log().ifStatusCodeIsEqualTo(302);
////
////        assertThat(response.log()).isEqualTo(301);
////    }
//
//    @Test
//    void newStudentWithLoginReturnsOk() {
//        var response = given()
//                .auth().basic("admin", "admin")
//                .get("/newstudent")
//                .then().extract().response();
//
//        assertThat(response.statusCode()).isEqualTo(200);
//    }
//
//
////    @Test
////    void newAdWithoutLoginReturns401() {
////        var response = given()
////                .get("/newad")
////                .then().extract().response();
////
////        assertThat(response.statusCode()).isEqualTo(401);
////    }
//
//    @Test
//    void newAdWithLoginReturnsOk() {
//        var response = given()
//                .auth().basic("admin", "admin")
//                .get("/newad")
//                .then().extract().response();
//
//        assertThat(response.statusCode()).isEqualTo(200);
//    }
//
////    @Test
////    void newCompanyWithoutLoginReturns401() {
////        var response = given()
////                .get("/newcompany")
////                .then().extract().response();
////
////        assertThat(response.statusCode()).isEqualTo(401);
////
////    }
//
////    @WithMockUser(authorities = "ADMIN")
////    @Test
////    void newCompanyWithLoginReturnsOk() {
////        var response = given()
////                .auth().basic("admin", "admin")
////                .get("/newcompany")
////                .then().extract().response();
////
////        assertThat(response.statusCode()).isEqualTo(200);
////    }
//}
