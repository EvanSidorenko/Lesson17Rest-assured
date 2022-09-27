package tests;

import models.MainData;
import models.UserData;
import models.UserRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static tests.Specs.*;
import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegresTests {
    @BeforeAll
    static void setUp() {
        RestAssured.filters(withCustomTemplates());
    }

    @Test
    void unsuccessfulRegisterTest() {
        UserRequest rq = new UserRequest();
        rq.setEmail("sydney@fife");
        given()
                .spec(request)
                .body(rq)
                .when()
                .post("/register")
                .then()
                .spec(response400);
    }

    @Test
    void successfulRegisterTest() {
        UserRequest rq = new UserRequest();
        rq.setEmail("eve.holt@reqres.in");
        rq.setPassword("pistol");
        given()
                .spec(request)
                .body(rq)
                .when()
                .post("/register")
                .then()
                .spec(response200);
    }

    @Test
    void getSingleUserTest() {
        MainData mainData =
                given()
                        .spec(request)
                        .when()
                        .get("/users/2")
                        .then()
                        .spec(response200)
                        .extract().as(MainData.class);
        assertEquals("janet.weaver@reqres.in", mainData.getData().getEmail());
        assertEquals("2", mainData.getData().getId());
        assertEquals("https://reqres.in/#support-heading", mainData.getSupport().getUrl());
        assertEquals("To keep ReqRes free, contributions towards server costs are appreciated!", mainData.getSupport().getText());
    }

    @Test
    void updateSingleUserTest() {
        UserRequest rq = new UserRequest();
        rq.setName("morpheus");
        rq.setJob("zion resident");
        UserData userData =
                given()
                        .spec(request)
                        .body(rq)
                        .when()
                        .put("/api/users/2")
                        .then()
                        .spec(response200)
                        .extract().as(UserData.class);
        assertEquals(rq.getName(), userData.getName());
        assertEquals(rq.getJob(), userData.getJob());
    }

    @Test
    void createSingleUserTest() {
        UserRequest rq = new UserRequest();
        rq.setName("morpheus");
        rq.setJob("leader");
        UserData userData =
                given()
                        .spec(request)
                        .body(rq)
                        .when()
                        .post("/api/users")
                        .then()
                        .spec(response201)
                        .extract().as(UserData.class);
        assertEquals(rq.getName(), userData.getName());
        assertEquals(rq.getJob(), userData.getJob());
    }
}

