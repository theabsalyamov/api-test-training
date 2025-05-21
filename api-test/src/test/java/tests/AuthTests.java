package tests;

import api.ProjectConfig;
import api.assertions.AssertableResponse;
import api.payloads.AuthPayloads;
import api.responses.AuthRegistryResponse;
import api.services.AuthApiService;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import net.datafaker.Faker;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Map;

import static api.conditions.Conditions.*;
import static org.hamcrest.Matchers.*;

public class AuthTests {

    // Если ты создаёшь объект, который не должен меняться и не должен быть доступен снаружи - делай его private final
//    private - защищает переменную от доступа и изменений извне.
//    final - защищает переменную от повторного присваивания внутри класса.
    private final AuthApiService authService = new AuthApiService();

    private static Faker faker;


    @BeforeAll
    public static void setUp(){



        ProjectConfig config = ConfigFactory.create(ProjectConfig.class, System.getProperties());
        faker = new Faker(new Locale(config.locale()));

        RestAssured.baseURI = config.baseUrl();
    }


    String fullName = faker.name().fullName();
    String email = faker.internet().emailAddress();
    String password = faker.internet().password(10, 20, true, true, true);

    @Test
    public void newUserRegistration() {

//        С помошью lombok мы можем так же писать такую цепочку вызовов сеттера
//        Ниже аналог вот этому:
//        AuthPayloads user = new AuthPayloads();
//        user.setFullName("Ы А П");
//        user.setEmail("test1209471-70@mail.ro");
//        user.setPassword("SSSSsafawfasf1");
//        user.setPasswordRepeat("SSSSsafawfasf1");

        AuthPayloads user = new AuthPayloads()
                .fullName(fullName)
                .email(email)
                .password(password)
                .passwordRepeat(password);

        authService.registryUser(user)
                .shouldHave(statusCode(201))
                .shouldHave(bodyField("id", notNullValue()))
                .shouldHave(bodyField("roles", hasItem("USER")))
                .shouldHave(bodyField("banned", equalTo(false)));
                // Проверка header, не обязательно
                //.shouldHave(header("Content-Type", "application/json"));

        //Чтобы получить header и работать с данными дальше
//        Headers headers = authService.registryUser(user)
//                .headers();

//        String contentType = headers.getValue("Content-Type");


//              asPojo() нужно использовать, если мы хотим дальше как то работать с данными. Например получить токен и использовать его в других тестах
//                .asPojo(AuthRegistryResponse.class);
//                 response.getId()


    }

    @Test
    public void sameUserRegistration() {


        AuthPayloads user = new AuthPayloads()
                .fullName(fullName)
                .email(email)
                .password(password)
                .passwordRepeat(password);

        authService.registryUser(user)
                .shouldHave(statusCode(201))
                .shouldHave(bodyField("id", notNullValue()))
                .shouldHave(bodyField("roles", hasItem("USER")))
                .shouldHave(bodyField("banned", equalTo(false)));

//                .then().log().all()// Проверка ответа (then)
//                .statusCode(201)// Проверка статуса
//                .body("id", notNullValue())// Проверка полей тела
//                .body("roles", hasItem("USER"))
//                .body("banned", equalTo(false));

        authService.registryUser(user)
                .shouldHave(statusCode(409))
                .shouldHave(bodyField("message", equalTo("Пользователь с таким email уже зарегистрирован")));
//                .then().log().all()// Проверка ответа (then)
//                .statusCode(409)// Проверка статуса
//                .body("message", equalTo("Пользователь с таким email уже зарегистрирован"));



    }
}
