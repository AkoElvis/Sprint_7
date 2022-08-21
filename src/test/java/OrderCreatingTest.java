import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static Constants.TestStand.BASE_URL;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class OrderCreatingTest {

    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int number;
    private String deliveryDate;
    private String comment;
    private List<String> color;


    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    this.firstName = "KolyaevOrder" + new Random().nextInt(100);
    this.lastName = "KolyaevOrder" + new Random().nextInt(100);
    this.address = "KolyaevOrder" + new Random().nextInt(100);
    this.metroStation = "KolyaevOrder" + new Random().nextInt(100);
    this.phone = "KolyaevOrder" + new Random().nextInt(100);
    this.number = new Random().nextInt(100);
    this.deliveryDate = "2022-08-" + new Random().nextInt(31);
    this.comment = "KolyaevOrder" + new Random().nextInt(100);
    this.color = new ArrayList<>();
    }

    @Test
    public void checkNoColorOrderCreating() {

        OrderCreating orderCreating = new OrderCreating(firstName, lastName, address, metroStation, phone, number, deliveryDate, comment, color);
        Response response = given()
                .header("Content-type", "application/json")
                .body(orderCreating)
                .when()
                .post("/api/v1/orders");
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
    }

    @Test
    public void checkGrayColorOrderCreating() {

        color.add("GRAY");
        OrderCreating orderCreating = new OrderCreating(firstName, lastName, address, metroStation, phone, number, deliveryDate, comment, color);
        Response response = given()
                .header("Content-type", "application/json")
                .body(orderCreating)
                .when()
                .post("/api/v1/orders");
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
    }

    @Test
    public void checkBlackColorOrderCreating() {

        color.add("BLACK");
        OrderCreating orderCreating = new OrderCreating(firstName, lastName, address, metroStation, phone, number, deliveryDate, comment, color);
        Response response = given()
                .header("Content-type", "application/json")
                .body(orderCreating)
                .when()
                .post("/api/v1/orders");
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
    }

    @Test
    public void checkGrayAndBlackColorOrderCreating() {

        color.add("BLACK");
        color.add("GRAY");

        OrderCreating orderCreating = new OrderCreating(firstName, lastName, address, metroStation, phone, number, deliveryDate, comment, color);
        Response response = given()
                .header("Content-type", "application/json")
                .body(orderCreating)
                .when()
                .post("/api/v1/orders");
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
    }

}
