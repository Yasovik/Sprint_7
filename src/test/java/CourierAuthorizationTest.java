import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.data.DataCourier;
import ru.yandex.practicum.data.DataSerialization;
import ru.yandex.practicum.data.api.CourierCreate;
import ru.yandex.practicum.data.api.CourierLogin;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierAuthorizationTest extends DataCourier {
    private final CourierCreate courierCreate = new CourierCreate();
    private final CourierLogin courierLogins = new CourierLogin();

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Авторизация существующего пользователя")
    public void courierAuthorization() {
        courierCreate.courierCreate(courierCreateData);
        Response courierLoginResponse = courierLogins.courierLogin(courierAuthorizatonData);
        courierLoginResponse.then().statusCode(200).assertThat().body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация курьера без логина")
    public void loginCourierWithoutLogin() {
        DataSerialization data = new DataSerialization("", courierPassword);
        courierCreate.courierCreate(courierCreateData);
        Response authorizationCourierWithoutLoginTest = courierLogins.courierLogin(data);
        authorizationCourierWithoutLoginTest.then().log().all().statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    public void loginCourierWithoutPassword() {
        DataSerialization data = new DataSerialization(courierLogin, "");
        courierCreate.courierCreate(courierCreateData);
        Response authorizationCourierWithoutLoginTest = courierLogins.courierLogin(data);
        authorizationCourierWithoutLoginTest.then().log().all().statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера без секции логина")
    public void loginCourierWithoutSectionLogin() {
        DataSerialization data = new DataSerialization(null, courierPassword);
        courierCreate.courierCreate(courierCreateData);
        Response authorizationCourierWithoutLoginTest = courierLogins.courierLogin(data);
        authorizationCourierWithoutLoginTest.then().log().all().statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера без секции пароль в запросе")
    public void loginCourierWithoutSectionPassword() {
        DataSerialization data = new DataSerialization(courierLogin, null);
        courierCreate.courierCreate(courierCreateData);
        Response authorizationCourierWithoutLoginTest = courierLogins.courierLogin(data);
        authorizationCourierWithoutLoginTest.then().log().all().statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера с некорректным логином")
    public void authorizationCourierWithIncorrectLoginTest() {
        DataSerialization data = new DataSerialization(courierLogin + "qwe", courierPassword);
        courierCreate.courierCreate(courierCreateData);
        Response authorizationCourierWithoutLoginTest = courierLogins.courierLogin(data);
        authorizationCourierWithoutLoginTest.then().log().all().statusCode(404).assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера с некорректным паролем")
    public void authorizationCourierWithIncorrectPasswordTest() {
        DataSerialization data = new DataSerialization(courierLogin, courierPassword + "55");
        courierCreate.courierCreate(courierCreateData);
        Response authorizationCourierWithoutLoginTest = courierLogins.courierLogin(data);
        authorizationCourierWithoutLoginTest.then().log().all().statusCode(404).assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void clear() {
        deleteCourier(courierLogin, courierPassword);
    }

}
