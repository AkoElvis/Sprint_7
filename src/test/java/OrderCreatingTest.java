import Constants.TestStandEndpoints;
import TestData.CreatingRandomData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
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
        RestAssured.baseURI = TestStandEndpoints.BASE_URL;
        this.firstName = CreatingRandomData.getRandomKolyaevString();
        this.lastName = CreatingRandomData.getRandomKolyaevString();
        this.address = CreatingRandomData.getRandomKolyaevString();
        this.metroStation = CreatingRandomData.getRandomKolyaevString();
        this.phone = CreatingRandomData.getRandomKolyaevString();
        this.number = CreatingRandomData.getRandomInt();
        this.deliveryDate = CreatingRandomData.getRandomDataAsString();
        this.comment = CreatingRandomData.getRandomKolyaevString();
        this.color = new ArrayList<>();
    }

    @Test
    @DisplayName("Checking the ability to create an order with an unspecified scooter color")
    @Description("Checking the body and status code of a successful response")
    public void checkNoColorOrderCreating() {
        Order order = new Order(firstName, lastName, address, metroStation, phone, number, deliveryDate, comment, color);
        Response response = order.getOrderCreatingResponse(order);
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
    }

    @Test
    @DisplayName("Checking the ability to create an order with the color of the scooter specified as GRAY")
    @Description("Checking the body and status code of a successful response")
    public void checkGrayColorOrderCreating() {
        color.add("GRAY");
        Order order = new Order(firstName, lastName, address, metroStation, phone, number, deliveryDate, comment, color);
        Response response = order.getOrderCreatingResponse(order);
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
    }

    @Test
    @DisplayName("Checking the ability to create an order with the color of the scooter specified as BLACK")
    @Description("Checking the body and status code of a successful response")
    public void checkBlackColorOrderCreating() {
        color.add("BLACK");
        Order order = new Order(firstName, lastName, address, metroStation, phone, number, deliveryDate, comment, color);
        Response response = order.getOrderCreatingResponse(order);
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
    }

    @Test
    @DisplayName("Checking the ability to create an order with the colors of the scooter specified as GRAY and BLACK")
    @Description("Checking the body and status code of a successful response")
    public void checkGrayAndBlackColorOrderCreating() {
        color.add("BLACK");
        color.add("GRAY");
        Order order = new Order(firstName, lastName, address, metroStation, phone, number, deliveryDate, comment, color);
        Response response = order.getOrderCreatingResponse(order);
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
    }
}
