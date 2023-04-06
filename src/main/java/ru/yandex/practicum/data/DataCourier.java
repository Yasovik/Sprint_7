package ru.yandex.practicum.data;

import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

public class DataCourier {
    protected String headersRequestContentType = "Content-type";
    protected String headersRequestApplication = "application/json";
    protected String loginCourierApi = "/api/v1/courier/login";
    protected String createCourierApi = "/api/v1/courier";
    protected String deleteCourierApi = "/api/v1/courier/";
    protected String courierLogin = "wqqanygignjafghggj";
    protected String courierPassword = "12345";
    protected String courierFirstName = "sasdaskeq";
    protected String clientFirstname = "Alex";
    protected String clientLastName = "Alex";
    protected String clientAddress = "Moscow";
    protected String clientMetroStation = "4";
    protected String clientPhone = "89251971085";
    protected int clientRentTime = 1;
    protected String clientDeliveryDate = "1";
    protected String clientComment = "AUF";
    protected String courierCreateData = "{\"login\": \"" + courierLogin + "\", \"password\": \"" + courierPassword + "\", \"firstName\": \"" + courierFirstName + "\"}";
    protected String courierAuthorizatonData = "{\"login\": \"" + courierLogin + "\", \"password\": \"" + courierPassword + "\"}";
    protected String createOrderApi = "/api/v1/orders";

    @Step("Удаление курьера")
    protected void deleteCourier(String login, String password) {
        LoginCourierRequestBody requestBody = new LoginCourierRequestBody(
                login,
                password
        );
        var responseBody = given()
                .header(headersRequestContentType, headersRequestApplication)
                .and()
                .body(requestBody)
                .when()
                .post(loginCourierApi)
                .as(LoginCourierResponseBody.class);

        given()
                .header(headersRequestContentType, headersRequestApplication)
                .and()
                .body(requestBody)
                .when()
                .pathParam("id", responseBody.getId())
                .delete(deleteCourierApi + "{id}");
    }

}
