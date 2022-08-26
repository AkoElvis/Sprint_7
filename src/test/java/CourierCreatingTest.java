import Constants.Messages;
import Constants.TestStandEndpoints;
import TestData.CreatingRandomData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
    @DisplayName("Checking the ability to create a courier")
    @Description("Checking the body and status code of a successful response")
    public void checkSuccessfulBodyAndCode() {
        Courier courier = new Courier(login,password,firstName );
        Response response = courier.getResponseCreateCourier(courier);
        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);
    }

    @Test
    @DisplayName("Checking the inability to create a courier without a login")
    @Description("Checking the body and status code of an unsuccessful response")
    public void checkNoLoginBodyAndCode() {
        Courier courier = new Courier("", password,firstName);
        Response response = courier.getResponseCreateCourier(courier);
        response.then().assertThat().body("message", equalTo(Messages.INCOMPLETE_DATA_TO_CREATE))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Checking the inability to create a courier without a password")
    @Description("Checking the body and status code of an unsuccessful response")
    public void checkNoPasswordBodyAndCode() {
        Courier courier = new Courier(login, "", firstName);
        Response response = courier.getResponseCreateCourier(courier);
        response.then().assertThat().body("message", equalTo(Messages.INCOMPLETE_DATA_TO_CREATE))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Checking the inability to create a courier without a login and password")
    @Description("Checking the body and status code of an unsuccessful response")
    public void checkNoLoginNoPasswordBodyAndCode() {
        Courier courier = new Courier("", "", firstName);
        Response response = courier.getResponseCreateCourier(courier);
        response.then().assertThat().body("message", equalTo(Messages.INCOMPLETE_DATA_TO_CREATE))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Checking the inability to create two identical couriers")
    @Description("Checking the body and status code of an unsuccessful response")
    public void checkExistedLoginBodyAndCode() {
        Courier courier = new Courier(login,password,firstName );
                courier.createCourier(courier);
        Response response = courier.getResponseCreateCourier(courier);
        response.then().assertThat().body("message", equalTo(Messages.EXISTED_LOGIN))
                .and()
                .statusCode(409);
    }
}
