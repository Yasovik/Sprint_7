import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.data.DataCourier;
import ru.yandex.practicum.data.DataSerialization;
import ru.yandex.practicum.data.api.CreateOrders;

import static org.hamcrest.Matchers.notNullValue;

public class CreateOrderTest extends DataCourier {
    CreateOrders createOrders = new CreateOrders();

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Создание заказа без выбора цвета")
    public void createOrderWithoutColorTest() {
        DataSerialization data = new DataSerialization(clientFirstname, clientLastName, clientAddress, clientMetroStation, clientPhone, clientRentTime, clientDeliveryDate, clientComment, new String[]{""});
        Response createOrderWithoutColorTestResponse = createOrders.CreateOrder(data);
        createOrderWithoutColorTestResponse.then().log().all().statusCode(201).assertThat().body("track", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа с выбором двух цветов")
    public void createOrderWithTwoColorTest() {
        DataSerialization data = new DataSerialization(clientFirstname, clientLastName, clientAddress, clientMetroStation, clientPhone, clientRentTime, clientDeliveryDate, clientComment, new String[]{"BLACK", "GREY"});
        Response createOrderWithTwoColorTestResponse = createOrders.CreateOrder(data);
        createOrderWithTwoColorTestResponse.then().log().all().statusCode(201).assertThat().body("track", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа с выбором черного цвета")
    public void createOrderWithColorBlackTest() {
        DataSerialization data = new DataSerialization(clientFirstname, clientLastName, clientAddress, clientMetroStation, clientPhone, clientRentTime, clientDeliveryDate, clientComment, new String[]{"BLACK"});
        Response createOrderWithColorBlackTestResponse = createOrders.CreateOrder(data);
        createOrderWithColorBlackTestResponse.then().log().all().statusCode(201).assertThat().body("track", notNullValue());
    }

    @Test
    @DisplayName("Проверка списка заказов")
    public void orderListTest() {
        CreateOrders getOrders = new CreateOrders();
        Response CreateOrdersResponse = getOrders.GetOrder("");
        CreateOrdersResponse.then().log().all().statusCode(200).assertThat().body("orders.id", notNullValue());
    }


}
