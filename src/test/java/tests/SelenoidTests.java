package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SelenoidTests {

    @Test
    void checkTotal() {
        given()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .body("total", is(20));
    }
    @Test
    void checkTotalWithoutGivenTotal() {
        get("https://selenoid.autotests.cloud/status")
                .then()
                .body("total", is(20));
    }
    @Test
    void checkWithSomeLogs() {
        given()
                .log().all()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .body("total", is(20));
    }
    @Test
    void checkChrome() {
        get("https://selenoid.autotests.cloud/status")
                .then()
                .body("browsers.chrome", hasKey("100.0"));
    }
    @Test
    void checkTotalBadPractice() {
       Response response =  get("https://selenoid.autotests.cloud/status")
                .then()
                .extract().response();
        System.out.println(response.asString());
        String expectedResponse = "{\"total\":20,\"used\":0,\"queued\":0,\"pending\":0,\"browsers\":{\"android\":{\"8.1\":{}},\"chrome\":{\"100.0\":{},\"99.0\":{}},\"chrome-mobile\":{\"86.0\":{}},\"firefox\":{\"97.0\":{},\"98.0\":{}},\"opera\":{\"84.0\":{},\"85.0\":{}}}}";
        assertEquals(expectedResponse,response.asString());
    }
    @Test
    void check401Status() {
        get("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .statusCode(401);
    }
    @Test
    void checkStatus200withAuth() {
        given()
                .auth().basic("user1","1234")
                .get("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .statusCode(200);
    }
}
