import Constants.TestStandEndpoints;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.notNullValue;

public class OrdersListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = TestStandEndpoints.BASE_URL;}

    @Test
    @DisplayName("Checking the ability to get an orders list")
    @Description("Checking the body and status code of a successful response")
    public void checkOrdersListCode200AndBodyNotNull() {
        Response response = Order.getOrdersListResponse();
        response.then().assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
    }
}
