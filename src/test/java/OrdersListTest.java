import Constants.TestStandEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class OrdersListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = TestStandEndpoints.BASE_URL;}

    @Test
    public void checkOrdersListCode200AndBodyNotNull() {

        Response response = given()
                .get(TestStandEndpoints.ORDER_CREATING_ENDPOINT);
        response.then().assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
    }
}
