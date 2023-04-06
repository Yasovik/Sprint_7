package ru.yandex.practicum.data.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.practicum.data.DataCourier;

import static io.restassured.RestAssured.given;

public class CourierCreate extends DataCourier {
    @Step("Создание нового курьера")
    public Response courierCreate(Object body) {
        return given()
                .header(headersRequestContentType, headersRequestApplication)
                .and()
                .body(body)
                .when().log().all()
                .post(createCourierApi);
    }

}
