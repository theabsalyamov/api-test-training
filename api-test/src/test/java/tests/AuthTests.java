package tests;

import api.ProjectConfig;
import api.assertions.AssertableResponse;
import api.payloads.AuthPayloads;
import api.responses.AuthRegistryResponse;
import api.services.AuthApiService;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import net.datafaker.Faker;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
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
    @Epic("Аутентификация")
    @Feature("Регистрация пользователя")
    @Story("Регистрация нового пользователя")
    @DisplayName("Успешная регистрация нового пользователя")
    @Description("Проверяет, что пользователь успешно создаётся с корректными данными")
    @Severity(SeverityLevel.CRITICAL)
    public void newUserRegistration() {

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
    @Epic("Аутентификация")
    @Feature("Регистрация пользователя")
    @Story("Попытка зарегистрировать уже существующего пользователя")
    @DisplayName("Регистрация с существующим email")
    @Description("Проверяет, что при повторной регистрации приходит 409 и сообщение об ошибке")
    @Severity(SeverityLevel.NORMAL)
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


        authService.registryUser(user)
                .shouldHave(statusCode(409))
                .shouldHave(bodyField("message", equalTo("Пользователь с таким email уже зарегистрирован")));




    }
}
