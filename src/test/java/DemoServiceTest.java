import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class DemoServiceTest {

    @BeforeClass
    public void initialSetup() {
        RestAssured.baseURI = "http://localhost:8090";
    }

    @Test(description = "Filter by Manufacturer")
    public void testFilterByManufacturer() {
        var manufacturer = "BMW";
        var reqBody = Car.builder().manufacturer(manufacturer).build();
        var response = RestAssured.given()
                .body(reqBody)
                .contentType(ContentType.JSON)
                .post("/cars")
                .then()
                .extract()
                .response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(response.statusCode() == 200, "Incorrect http status code");
        var cars = response.body().jsonPath().getList("data.cars", Car.class);
        softAssert.assertTrue(!cars.isEmpty(), "No cars found");
        cars.forEach(car -> softAssert.assertTrue(car.getManufacturer().equalsIgnoreCase(manufacturer), "Incorrect manufacturer"));
        softAssert.assertAll();
    }

    @Test(description = "Filter by Model")
    public void testFilterByModel() {
        var model = "C 63 AMG";
        var reqBody = Car.builder().model(model).build();
        var response = RestAssured.given()
                .body(reqBody)
                .contentType(ContentType.JSON)
                .post("/cars")
                .then()
                .extract()
                .response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(response.statusCode() == 200, "Incorrect http status code");
        var cars = response.body().jsonPath().getList("data.cars", Car.class);
        softAssert.assertTrue(!cars.isEmpty(), "No cars found");
        cars.forEach(car -> softAssert.assertTrue(car.getModel().equalsIgnoreCase(model), "Incorrect model"));
        softAssert.assertAll();
    }

    @Test(description = "Filter when no cars found")
    public void testWhenNoCarsFound() {
        var manufacturer = "Nissan";
        var reqBody = Car.builder().manufacturer(manufacturer).build();
        var response = RestAssured.given()
                .body(reqBody)
                .contentType(ContentType.JSON)
                .post("/cars")
                .then()
                .extract()
                .response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(response.statusCode() == 200, "Incorrect http status code");
        var error = response.body().jsonPath().getString("error.empty");
        softAssert.assertEquals(error, "No cars found", "Incorrect error");
        softAssert.assertAll();
    }
}