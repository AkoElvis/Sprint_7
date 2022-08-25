//POJO Courier для передачи в запрос JSON как объект класса

import Constants.TestStandEndpoints;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Courier {

private String login;
private String password;
private String firstName;

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public Courier() {
    }

    public Courier(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Response getResponseCreateCourier(Object body) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(body)
                .when()
                .post(TestStandEndpoints.COURIER_CREATE_ENDPOINT);
    }

    public Response getResponseLoginCourier(Object body) {
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .when()
                .post(TestStandEndpoints.COURIER_LOGIN_ENDPOINT);
    }
}
