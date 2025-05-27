package tests.Auth;
import static cinescope.api.conditions.Conditions.statusCode;
import static cinescope.api.conditions.Conditions.bodyField;

import cinescope.api.ProjectConfig;
import cinescope.api.payloads.LoginPayloads;
import cinescope.api.payloads.RegisterPayloads;
import cinescope.api.services.AuthApiService;
import cinescope.api.utils.PasswordGenerator;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import net.datafaker.Faker;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.hamcrest.Matchers.*;

public class AuthTests {

    // Если ты создаёшь объект, который не должен меняться и не должен быть доступен снаружи - делай его private final
//    private - защищает переменную от доступа и изменений извне.
//    final - защищает переменную от повторного присваивания внутри класса.
    private final AuthApiService authService = new AuthApiService();

    private static Faker faker;

    private String fullName;
    private String email;
    private String password;


    @BeforeAll
    public static void setUp(){

        ProjectConfig config = ConfigFactory.create(ProjectConfig.class, System.getProperties());
        faker = new Faker(new Locale(config.locale()));
    }


    @BeforeEach
    public void prepareDataAndToken() {

        fullName = faker.name().fullName();
        email = faker.internet().emailAddress();
        password = PasswordGenerator.generateValidPassword();
    }


    @Test
    @Epic("Аутентификация")
    @Feature("Регистрация пользователя")
    @Story("Регистрация нового пользователя")
    @DisplayName("Успешная регистрация нового пользователя")
    @Severity(SeverityLevel.CRITICAL)
    public void newUserRegistration() {

        RegisterPayloads user = new RegisterPayloads()
                .fullName(fullName)
                .email(email)
                .password(password)
                .passwordRepeat(password);

        authService.registryUser(user)
                .shouldHave(statusCode(201))
                .shouldHave(bodyField("verified", equalTo(true)))
                .shouldHave(bodyField("roles", not(empty())));
                // Проверка header, не обязательно
                //.shouldHave(header("Content-Type", "application/json"));

//        Чтобы получить header и работать с данными дальше
//        Headers headers = authService.registryUser(user)
//                .headers();

//        String contentType = headers.getValue("Content-Type");


//        asPojo() нужно использовать, если мы хотим дальше как то работать с данными. Например получить токен и использовать его в других тестах
//                .asPojo(AuthRegistryResponse.class);
//                 response.getId()


    }

    @Test
    @Epic("Аутентификация")
    @Feature("Регистрация пользователя")
    @Story("Попытка зарегистрировать уже существующего пользователя")
    @DisplayName("Регистрация с существующим email")
    @Severity(SeverityLevel.NORMAL)
    public void sameUserRegistration() {


        RegisterPayloads user = new RegisterPayloads()
                .fullName(fullName)
                .email(email)
                .password(password)
                .passwordRepeat(password);

        authService.registryUser(user)
                .shouldHave(statusCode(201))
                .shouldHave(bodyField("verified", equalTo(true)))
                .shouldHave(bodyField("roles", not(empty())));


        authService.registryUser(user)
                .shouldHave(statusCode(409))
                .shouldHave(bodyField("message", equalTo("Пользователь с таким email уже зарегистрирован")));

    }

    @Test
    @Epic("Аутентификация")
    @Feature("Авторизация пользователя")
    @Story("Авторизация пользователя")
    @DisplayName("Успешная авторизация пользователя")
    @Description("Проверяет, что пользователь успешно авторизовался с корректными данными")
    @Severity(SeverityLevel.CRITICAL)
    public void userLogin() {

        RegisterPayloads user = new RegisterPayloads()
                .fullName(fullName)
                .email(email)
                .password(password)
                .passwordRepeat(password);

        LoginPayloads loginUser = new LoginPayloads()
                .email(email)
                .password(password);


        authService.registryUser(user)
                .shouldHave(statusCode(201))
                .shouldHave(bodyField("verified", equalTo(true)))
                .shouldHave(bodyField("roles", not(empty())));

        authService.loginUser(loginUser)
                .shouldHave(statusCode(201))
                .shouldHave(bodyField("accessToken", notNullValue()))
                .shouldHave(bodyField("expiresIn", notNullValue()));




    }
}
