import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static Constants.TestStand.BASE_URL;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class OrdersListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;}

    @Test
    public void checkOrdersListCode200AndBodyNotNull() {

        Response response = given()
                .get("/api/v1/orders");
        response.then().assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
    }
}
