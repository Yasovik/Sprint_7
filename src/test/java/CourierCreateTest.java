import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.data.DataCourier;
import ru.yandex.practicum.data.DataSerialization;
import ru.yandex.practicum.data.api.CourierCreate;

import static org.hamcrest.Matchers.equalTo;

public class CourierCreateTest extends DataCourier {
    CourierCreate courierCreate = new CourierCreate();

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Создание нового курьера")
    public void createNewCourierTest() {
        Response courierCreateResponse = courierCreate.CourierCreate(courierCreateData);
        courierCreateResponse.then().statusCode(201).assertThat().body("ok", equalTo(true));
        deleteCourier();
    }

    @Test
    @DisplayName("Создание 2х одинаковых курьеров")
    public void doubleCreateCourierTest() {
        Response courierCreateResponse = courierCreate.CourierCreate(courierCreateData);
        courierCreateResponse.then().statusCode(201).assertThat().body("ok", equalTo(true));
        Response courierCreateResponseDouble = courierCreate.CourierCreate(courierCreateData);
        deleteCourier();
        courierCreateResponseDouble.then().statusCode(409).assertThat().body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Создание курьера без логина")
    public void createCourierWithoutLoginTest() {
        DataSerialization data = new DataSerialization("", courierPassword, courierFirstName);
        Response createCourierWithoutLoginTest = courierCreate.CourierCreate(data);
        createCourierWithoutLoginTest.then().log().all().statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    public void createCourierWithoutPasswordTest() {
        DataSerialization data = new DataSerialization(courierLogin, "", courierFirstName);
        Response createCourierWithoutPasswordTest = courierCreate.CourierCreate(data);
        createCourierWithoutPasswordTest.then().log().all().statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без фамилии")
    public void createCourierWithoutFirstNameTest() {
        DataSerialization data = new DataSerialization(courierLogin, courierPassword, "");
        Response createCourierWithoutFirstNameTest = courierCreate.CourierCreate(data);
        deleteCourier();
        createCourierWithoutFirstNameTest.then().log().all().statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без секции фамилия")
    public void createCourierWithoutSectionFirstNameTest() {
        DataSerialization data = new DataSerialization(courierLogin, courierPassword, null);
        Response createCourierWithoutSectionFirstNameTest = courierCreate.CourierCreate(data);
        deleteCourier();
        createCourierWithoutSectionFirstNameTest.then().log().all().statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без секции пароль")
    public void createCourierWithoutSectionPasswordTest() {
        DataSerialization data = new DataSerialization(courierLogin, null, courierFirstName);
        Response createCourierWithoutSectionPasswordTest = courierCreate.CourierCreate(data);
        createCourierWithoutSectionPasswordTest.then().log().all().statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без секции пароль")
    public void createCourierWithoutSectionLoginTest1() {
        DataSerialization data = new DataSerialization(null, courierPassword, courierFirstName);
        Response createCourierWithoutSectionLoginTest = courierCreate.CourierCreate(data);
        createCourierWithoutSectionLoginTest.then().log().all().statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }


}


