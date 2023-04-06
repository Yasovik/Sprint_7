package ru.yandex.practicum.data.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.practicum.data.DataCourier;

import static io.restassured.RestAssured.given;

public class CreateOrders extends DataCourier {
    @Step("Создание заказа")
    public Response createOrder(Object body) {
        return given()
                .header(headersRequestContentType, headersRequestApplication)
                .and()
                .body(body)
                .when().log().all()
                .post(createOrderApi);
    }

    @Step("Получение заказа")
    public Response getOrder(Object body) {
        return given()
                .header(headersRequestContentType, headersRequestApplication)
                .and()
                .body(body)
                .when().log().all()
                .get(createOrderApi);
    }

}

