package com.restful.booker.testsuite;

import com.restful.booker.propertyreader.PropertyReader;
import com.restful.booker.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class GetBookingUsingId extends TestBase {
    static ValidatableResponse response;
    static List<Integer> bookingIds;
    String getBooking = PropertyReader.getInstance().getProperty("getBooking");

    @BeforeClass
    public void getBookings() {
        response = given()
                .when()
                .get(getBooking)
                .then();
        response.statusCode(200);
        bookingIds = response.extract().path("bookingid");
    }

    @Test
    public void getBookingId() {
        int numRandom = (int) (Math.random() * bookingIds.size() - 1);
        int randomIndex = bookingIds.get(numRandom);
        response = given()
                .pathParam("id", randomIndex)
                .when()
                .get("booking/{id}")
                .then();

        //status code
        response.statusCode(200);
        //random id
        response.log().all();
//verifying if response body is not empty
        Map<String, ?> bookingDetails = response.extract().path("$");
        Assert.assertTrue(!bookingDetails.isEmpty());


    }
}
