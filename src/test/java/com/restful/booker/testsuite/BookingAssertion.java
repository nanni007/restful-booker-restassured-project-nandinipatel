package com.restful.booker.testsuite;

import com.restful.booker.propertyreader.PropertyReader;
import com.restful.booker.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class BookingAssertion extends TestBase {
    static ValidatableResponse response;
    String getBooking = PropertyReader.getInstance().getProperty("getBooking");

    @Test
    public void createBooking() {
        HashMap data = new HashMap();
        data.put("firstname", "Primer");
        data.put("lastname", "Tester");
        data.put("totalprice", "123456");
        data.put("depositpaid", true);
        HashMap<String, String> bookingDates = new HashMap<>() {
            {
                put("checkin", "2018-01-01");
                put("checkout", "2019-01-01");
            }
        };
        data.put("bookingdates", bookingDates);
        data.put("additionalneeds", "Breakfast");

        response = given()
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .header("Content-Type", "application/json")
                .when()
                .body(data)
                .post(getBooking)
                .then();
        //header
        response.header("Content-Type", "application/json;charset=utf-8");
        //status code
        response.statusCode(200);
        //time
        response.time((Matchers.lessThan(2000L)));
        //Body
        response.body("booking.firstname", equalTo(data.get("firstname")));
        response.body("booking.lastname", equalTo(data.get("lastname")));
        response.body("booking.totalprice", equalTo(data.get("totalprice")));
        response.body("booking.depositpaid", equalTo(data.get("depositpaid")));
        response.body("booking.bookingdates", equalTo(data.get("bookingdates")));
        response.body("booking.additionalneeds", equalTo(data.get("additionalneeds")));

        //new booking
        response.body("$", hasKey("bookingid"));
    }
}



