import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static Constants.TestStand.BASE_URL;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CourierCreatingTest {

    private String login;
    private String password;
    private String firstName;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        this.firstName = "KolyaevCourierCreatingTest" + new Random().nextInt(100);
        this.login = "KolyaevCourierCreatingTest" + new Random().nextInt(100);
        this.password = "KolyaevCourierCreatingTest" + new Random().nextInt(100);
    }

    @After
    public void deleteCreatedCourier() {
        Courier createdCourier = new Courier(login,password);
        Response courierLoginResponse = given()
                .header("Content-type", "application/json")
                .body(createdCourier)
                .when()
                .post("/api/v1/courier/login");
        CourierId courierId = courierLoginResponse.body().as(CourierId.class);

        int id = courierId.getId();

        given()
                .header("Content-type", "application/json")
                .body("{ \"id\": \"" + id + "\"}")
                .delete("/api/v1/courier/" + id);
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
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    public void checkNoPasswordBodyAndCode() {

        Courier courier = new Courier(login, "", firstName);
        Response response = courier.getResponseCreateCourier(courier);;
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    public void checkNoLoginNoPasswordBodyAndCode() {

        Courier courier = new Courier("", "", firstName);
        Response response = courier.getResponseCreateCourier(courier);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    //Не смог придумать как сделать этот тест по-нормальному независимым. Хотелось бы использовать здесь способ подготовки
    //тестовых данных "Запрос данных у приложения", сходить в таблицу Couriers и взять там первый из массива id, но не вижу
    //в документации такой ручки
    @Test
    public void checkExistedLoginBodyAndCode() {

        Courier courier = new Courier(login,password,firstName );
                courier.getResponseCreateCourier(courier);

        Response response = courier.getResponseCreateCourier(courier);

        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
    }
}
