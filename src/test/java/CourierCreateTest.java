import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.data.DataCourier;
import ru.yandex.practicum.data.DataSerialization;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierCreateTest extends DataCourier {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void createNewCourierTest() {
        Response response =
                given()
                        .header(headersRequestContentType, headersRequestApplication)
                        .and()
                        .body(courierCreateData)
                        .when()
                        .post(createCourierApi);
        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);
        System.out.println(response.body().asString());
    }

    @Test
    public void doubleCreateCourierTest() {
        Response response =
                given()
                        .header(headersRequestContentType, headersRequestApplication)
                        .and()
                        .body(courierCreateData)
                        .when()
                        .post(createCourierApi);
        Response responseDouble =
                given()
                        .header(headersRequestContentType, headersRequestApplication)
                        .and()
                        .body(courierCreateData)
                        .when()
                        .post(createCourierApi);
        deleteCourier();
        responseDouble.then().assertThat().body("message", equalTo("Этот логин уже используется"))
                .and()
                .statusCode(409);
        System.out.println(response.body().asString());

    }

    @Test
    public void createCourierWithoutLoginTest() {
        DataSerialization data = new DataSerialization("", courierPassword, courierFirstName);
        Response response =
                given()
                        .header(headersRequestContentType, headersRequestApplication)
                        .and()
                        .body(data)
                        .when()
                        .post(createCourierApi);
        response.then().assertThat().body("code", equalTo(400))
                .and()
                .statusCode(400);
        System.out.println(response.body().asString());
    }

    @Test
    public void createCourierWithoutPasswordTest() {
        DataSerialization data = new DataSerialization(courierLogin, "", courierFirstName);
        Response response =
                given()
                        .header(headersRequestContentType, headersRequestApplication)
                        .and()
                        .body(data)
                        .when()
                        .post(createCourierApi);
        response.then().assertThat().body("code", equalTo(400))
                .and()
                .statusCode(400);
        System.out.println(response.body().asString());
    }

    @Test
    public void createCourierWithoutFirstNameTest() {
        DataSerialization data = new DataSerialization(courierLogin, courierPassword, "");
        Response response =
                given()
                        .header(headersRequestContentType, headersRequestApplication)
                        .and()
                        .body(data)
                        .when()
                        .post(createCourierApi);
        deleteCourier();
        response.then().assertThat().body("code", equalTo(400))
                .and()
                .statusCode(400);
        System.out.println(response.body().asString());
    }

    @Test
    public void createCourierWithoutSectionFirstNameTest() {
        DataSerialization data = new DataSerialization(courierLogin, courierPassword, null);
        Response response =
                given()
                        .header(headersRequestContentType, headersRequestApplication)
                        .and()
                        .body(data)
                        .when()
                        .post(createCourierApi);
        deleteCourier();
        response.then().assertThat().body("code", equalTo(400))
                .and()
                .statusCode(400);
        System.out.println(response.body().asString());
    }

    @Test
    public void createCourierWithoutSectionPasswordTest() {
        DataSerialization data = new DataSerialization(courierLogin, null, courierFirstName);
        Response response =
                given()
                        .header(headersRequestContentType, headersRequestApplication)
                        .and()
                        .body(data)
                        .when()
                        .post(createCourierApi);
        response.then().assertThat().body("code", equalTo(400))
                .and()
                .statusCode(400);
        System.out.println(response.body().asString());
    }

    @Test
    public void createCourierWithoutSectionLoginTest() {
        DataSerialization data = new DataSerialization(null, courierPassword, courierFirstName);
        Response response =
                given()
                        .header(headersRequestContentType, headersRequestApplication)
                        .and()
                        .body(data)
                        .when()
                        .post(createCourierApi);
        response.then().assertThat().body("code", equalTo(400))
                .and()
                .statusCode(400);
        System.out.println(response.body().asString());
    }

    @Test
    public void courierAuthorization() {
        createNewCourier();
        Response response =
                given()
                        .header(headersRequestContentType, headersRequestApplication)
                        .and()
                        .body(courierAuthorizatonData)
                        .when()
                        .post(loginCourierApi);
        response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
        System.out.println(response.body().asString());
        deleteCourier();

    }

    @Test
    public void loginCourierWithoutLogin() {
        DataSerialization data = new DataSerialization("", courierPassword);
        createNewCourier();
        Response response =
                given()
                        .header(headersRequestContentType, headersRequestApplication)
                        .and()
                        .body(data)
                        .when()
                        .post(loginCourierApi);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
        System.out.println(response.body().asString());
        deleteCourier();

    }

    @Test
    public void loginCourierWithoutPassword() {
        DataSerialization data = new DataSerialization(courierLogin, "");
        createNewCourier();
        Response response =
                given()
                        .header(headersRequestContentType, headersRequestApplication)
                        .and()
                        .body(data)
                        .when()
                        .post(loginCourierApi);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
        System.out.println(response.body().asString());
        deleteCourier();
    }

    @Test
    public void loginCourierWithoutSectionLogin() {
        DataSerialization data = new DataSerialization(null, courierPassword);
        createNewCourier();
        Response response =
                given()
                        .header(headersRequestContentType, headersRequestApplication)
                        .and()
                        .body(data)
                        .when()
                        .post(loginCourierApi);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
        System.out.println(response.body().asString());
        deleteCourier();

    }

    @Test
    public void loginCourierWithoutSectionPassword() {
        DataSerialization data = new DataSerialization(courierLogin, null);
        createNewCourier();
        Response response =
                given()
                        .header(headersRequestContentType, headersRequestApplication)
                        .and()
                        .body(data)
                        .when()
                        .post(loginCourierApi);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
        System.out.println(response.body().asString());
        deleteCourier();
    }

    @Test
    public void authorizationCourierWithIncorrectLoginTest() {
        DataSerialization data = new DataSerialization(courierLogin + "qwe", courierPassword);
        createNewCourier();
        Response response =
                given()
                        .header(headersRequestContentType, headersRequestApplication)
                        .and()
                        .body(data)
                        .when()
                        .post(loginCourierApi);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
        System.out.println(response.body().asString());
        deleteCourier();
    }

    @Test
    public void authorizationCourierWithIncorrectPasswordTest() {
        DataSerialization data = new DataSerialization(courierLogin, courierPassword + "55");
        createNewCourier();
        Response response =
                given()
                        .header(headersRequestContentType, headersRequestApplication)
                        .and()
                        .body(data)
                        .when()
                        .post(loginCourierApi);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
        System.out.println(response.body().asString());
        deleteCourier();
    }
}


