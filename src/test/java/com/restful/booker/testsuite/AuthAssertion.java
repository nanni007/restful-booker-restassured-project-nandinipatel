package com.restful.booker.testsuite;

import com.restful.booker.model.AuthPojo;
import com.restful.booker.propertyreader.PropertyReader;
import com.restful.booker.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;

public class AuthAssertion extends TestBase {
    static String username = "admin";
    static String password = "password123";
    static ValidatableResponse response;
    static String token;
    String authCreate = PropertyReader.getInstance().getProperty("authCreate");
@BeforeClass
    public  void tokenForAuthCreate() {
    AuthPojo authPojo = new AuthPojo();
    authPojo.setUsername(username);
    authPojo.setPassword(password);
    response = given()
            .header("Content-Type", "application/json")
            .when()
            .body(authPojo)
            .post(authCreate)
            .then()
            .header("Content-Type", "application/json; charser=utf-8")
            .statusCode(200);
}
@Test
    public void tokenVerification(){
    response.body("$",hasKey("token"));
}
}
