import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.data.DataCourier;
import ru.yandex.practicum.data.DataSerialization;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CreateOrderTest extends DataCourier {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void createOrderWithoutColorTest() {
        DataSerialization data = new DataSerialization(clientFirstname, clientLastName, clientAddress, clientMetroStation, clientPhone, clientRentTime, clientDeliveryDate, clientComment, new String[]{""});
        Response response =
                given()
                        .header(headersRequestContentType, headersRequestApplication)
                        .and()
                        .body(data)
                        .when()
                        .post(createOrderApi);
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
        System.out.println(response.body().asString());
    }

    @Test
    public void createOrderWithTwoColorTest() {
        DataSerialization data = new DataSerialization(clientFirstname, clientLastName, clientAddress, clientMetroStation, clientPhone, clientRentTime, clientDeliveryDate, clientComment, new String[]{"BLACK", "GREY"});
        Response response =
                given()
                        .header(headersRequestContentType, headersRequestApplication)
                        .and()
                        .body(data)
                        .when()
                        .post(createOrderApi);
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
        System.out.println(response.body().asString());
    }

    @Test
    public void createOrderWithColorBlackTest() {
        DataSerialization data = new DataSerialization(clientFirstname, clientLastName, clientAddress, clientMetroStation, clientPhone, clientRentTime, clientDeliveryDate, clientComment, new String[]{"BLACK"});
        Response response =
                given()
                        .header(headersRequestContentType, headersRequestApplication)
                        .and()
                        .body(data)
                        .when()
                        .post(createOrderApi);
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
        System.out.println(response.body().asString());
    }

    @Test
    public void orderListTest() {
        Response response =
                given()
                        .header(headersRequestContentType, headersRequestApplication)
                        .and()
                        .when()
                        .get(createOrderApi);
        response.then().assertThat().body("orders.id", notNullValue())
                .and()
                .statusCode(200);
        System.out.println(response.body().asString());
    }


}
