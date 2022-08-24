import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static Constants.TestStand.BASE_URL;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest {

    private String login;
    private String password;
    private Response response;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;

        String firstName = "KolyaevCourierLoginTest" + new Random().nextInt(100);
        this.login = "KolyaevCourierLoginTest" + new Random().nextInt(100);
        this.password = "KolyaevCourierLoginTest" + new Random().nextInt(100);

        Courier courier = new Courier(login, password, firstName);
                given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post("/api/v1/courier");
    }

    @After
    public void deleteCreatedCourier() {
        if (response.statusCode() == 200) {
            CourierId courierId = response.body().as(CourierId.class);
            int id = courierId.getId();
            given()
                .header("Content-type", "application/json")
                .body("{ \"id\": \"" + id + "\"}")
                .delete("/api/v1/courier/" + id);
        }
    }

    @Test
    public void checkCorrectLoginAndPasswordBodyAndCode() {
        Courier createdCourier = new Courier(login, password);
        this.response = createdCourier.getResponseLoginCourier(createdCourier);
        response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    public void checkNoLoginBodyAndCode() {
        Courier createdCourier = new Courier("", password);
        this.response = createdCourier.getResponseLoginCourier(createdCourier);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    public void checkNoPasswordBodyAndCode() {
        Courier createdCourier = new Courier(login, "");
        this.response = createdCourier.getResponseLoginCourier(createdCourier);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    public void checkNoLoginNoPasswordBodyAndCode() {
        Courier createdCourier = new Courier("", "");
        this.response = createdCourier.getResponseLoginCourier(createdCourier);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    public void checkNotExistedLoginBodyAndCode() {
        Courier createdCourier = new Courier("KolyaevCourierLoginTest" + new Random().nextInt(100) , "KolyaevCourierLoginTest" + new Random().nextInt(100));
        this.response = createdCourier.getResponseLoginCourier(createdCourier);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }
}
