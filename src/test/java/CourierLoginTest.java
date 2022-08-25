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
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest {

    private String login;
    private String password;
    private Response response;

    @Before
    public void setUp() {
        RestAssured.baseURI = TestStandEndpoints.BASE_URL;
        String firstName = CreatingRandomData.getRandomKolyaevString();
        this.login = CreatingRandomData.getRandomKolyaevString();
        this.password = CreatingRandomData.getRandomKolyaevString();
        Courier courier = new Courier(login, password, firstName);
        Courier.createCourier(courier);
    }

    //заметил ошибку, в прошлой версии удалялись тестовые данные только курьера из теста checkCorrectLoginAndPasswordBodyAndCode
    //от остальных 4 тестов оставались лог/пасс неудаленными
    @After
    public void deleteCreatedCourier() {
        Courier createdCourier = new Courier(login,password);
        Response responseCreatedCourier = createdCourier.getResponseLoginCourier(createdCourier);
        CourierId courierId = responseCreatedCourier.body().as(CourierId.class);
        Courier.deleteCourier(courierId.getId());
    }

    @Test
    @DisplayName("Checking the ability to log in with valid credentials")
    @Description("Checking the body and status code of a successful response")
    public void checkCorrectLoginAndPasswordBodyAndCode() {
        Courier courier = new Courier(login, password);
        this.response = courier.getResponseLoginCourier(courier);
        response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Checking the inability to log in without a login")
    @Description("Checking the body and status code of an unsuccessful response")
    public void checkNoLoginBodyAndCode() {
        Courier courier = new Courier("", password);
        this.response = courier.getResponseLoginCourier(courier);
        response.then().assertThat().body("message", equalTo(Messages.INCOMPLETE_DATA_TO_LOGIN))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Checking the inability to log in without a password")
    @Description("Checking the body and status code of an unsuccessful response")
    public void checkNoPasswordBodyAndCode() {
        Courier courier = new Courier(login, "");
        this.response = courier.getResponseLoginCourier(courier);
        response.then().assertThat().body("message", equalTo(Messages.INCOMPLETE_DATA_TO_LOGIN))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Checking the inability to log in without a login and password")
    @Description("Checking the body and status code of an unsuccessful response")
    public void checkNoLoginNoPasswordBodyAndCode() {
        Courier courier = new Courier("", "");
        this.response = courier.getResponseLoginCourier(courier);
        response.then().assertThat().body("message", equalTo(Messages.INCOMPLETE_DATA_TO_LOGIN))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Checking the inability to log in with non-existent credentials")
    @Description("Checking the body and status code of an unsuccessful response")
    public void checkNotExistedLoginBodyAndCode() {
        Courier courier = new Courier(CreatingRandomData.getRandomKolyaevString(), CreatingRandomData.getRandomKolyaevString());
        this.response = courier.getResponseLoginCourier(courier);
        response.then().assertThat().body("message", equalTo(Messages.NOT_EXISTED_LOGIN))
                .and()
                .statusCode(404);
    }
}
