import Constants.Messages;
import Constants.TestStandEndpoints;
import TestData.CreatingRandomData;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CourierCreatingTest {

    private String login;
    private String password;
    private String firstName;

    @Before
    public void setUp() {
        RestAssured.baseURI = TestStandEndpoints.BASE_URL;
        this.firstName = CreatingRandomData.getRandomKolyaevString();
        this.login = CreatingRandomData.getRandomKolyaevString();
        this.password = CreatingRandomData.getRandomKolyaevString();
    }

    @After
    public void deleteCreatedCourier() {
        Courier createdCourier = new Courier(login,password);
        Response courierLoginResponse = createdCourier.getResponseLoginCourier(createdCourier);
        CourierId courierId = courierLoginResponse.body().as(CourierId.class);
        Courier.deleteCourier(courierId.getId());
    }

    @Test
    public void checkSuccessfulBodyAndCode() {
        Courier courier = new Courier(login,password,firstName );
        Response response = courier.getResponseCreateCourier(courier);
        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);
    }

    @Test
    public void checkNoLoginBodyAndCode() {
        Courier courier = new Courier("", password,firstName);
        Response response = courier.getResponseCreateCourier(courier);
        response.then().assertThat().body("message", equalTo(Messages.INCOMPLETE_DATA_TO_CREATE))
                .and()
                .statusCode(400);
    }

    @Test
    public void checkNoPasswordBodyAndCode() {

        Courier courier = new Courier(login, "", firstName);
        Response response = courier.getResponseCreateCourier(courier);
        response.then().assertThat().body("message", equalTo(Messages.INCOMPLETE_DATA_TO_CREATE))
                .and()
                .statusCode(400);
    }

    @Test
    public void checkNoLoginNoPasswordBodyAndCode() {

        Courier courier = new Courier("", "", firstName);
        Response response = courier.getResponseCreateCourier(courier);
        response.then().assertThat().body("message", equalTo(Messages.INCOMPLETE_DATA_TO_CREATE))
                .and()
                .statusCode(400);
    }

    @Test
    public void checkExistedLoginBodyAndCode() {

        Courier courier = new Courier(login,password,firstName );
                courier.getResponseCreateCourier(courier);
        Response response = courier.getResponseCreateCourier(courier);
        response.then().assertThat().body("message", equalTo(Messages.EXISTED_LOGIN))
                .and()
                .statusCode(409);
    }
}
