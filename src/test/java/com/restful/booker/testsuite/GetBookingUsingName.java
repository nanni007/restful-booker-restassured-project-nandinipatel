package com.restful.booker.testsuite;

import com.restful.booker.propertyreader.PropertyReader;
import com.restful.booker.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;

public class GetBookingUsingName extends TestBase {
    String getBooking = PropertyReader.getInstance().getProperty("getBooking");

    @Test
    public void getBooking() {

        Map<String, String> queParam = new HashMap<>();
        queParam.put("firstname", "Sally");
        queParam.put("lastname", "Brown");
        ValidatableResponse response = given()
                .queryParams(queParam)
                .when()
                .get(getBooking)
                .then()
                .header("Content-Type", "application/json;charset=utf-8")
                .body("[0]", hasKey("bookingid"))
                .statusCode(200);
        response.log().all();
        List<HashMap<String, Object>> records = response.extract().path("$");

        Assert.assertFalse(records.isEmpty());
    }

}
