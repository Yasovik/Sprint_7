package ru.yandex.practicum.data.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.practicum.data.DataCourier;

import static io.restassured.RestAssured.given;

public class CourierLogin extends DataCourier {
    @Step("Авторизация курьера")
    public Response courierLogin(Object body) {
        return given()
                .header(headersRequestContentType, headersRequestApplication)
                .and()
                .body(body)
                .when().log().all()
                .post(loginCourierApi);
    }

}
