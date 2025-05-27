package tests.Auth;

import cinescope.api.ProjectConfig;
import cinescope.api.payloads.LoginPayloads;
import cinescope.api.payloads.UserPayloads;
import cinescope.api.responses.UserInfoResponse;
import cinescope.api.services.AuthApiService;
import cinescope.api.utils.DBUtils;
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

import static cinescope.api.conditions.Conditions.bodyField;
import static cinescope.api.conditions.Conditions.statusCode;
import static org.hamcrest.Matchers.*;

public class UserTests {

    private static DBUtils DbUtils;
    private final AuthApiService authService = new AuthApiService();

    private static Faker faker;

    private String bearerToken;

    private String fullName;
    private String email;
    private String password;
    private static String adminUserName;
    private static String adminUserPassword;


    @BeforeAll
    public static void setUp(){

        ProjectConfig config = ConfigFactory.create(ProjectConfig.class, System.getProperties());
        faker = new Faker(new Locale(config.locale()));

        adminUserName = config.adminUserName();
        adminUserPassword = config.adminUserPassword();

    }

    @BeforeEach
    public void prepareDataAndToken(){

        fullName = faker.name().fullName();
        email = faker.internet().emailAddress();
        password = PasswordGenerator.generateValidPassword();

// Авторизация и сохранение токена
        LoginPayloads loginUser = new LoginPayloads()
                .email(adminUserName)
                .password(adminUserPassword);

        UserInfoResponse response = authService.loginUser(loginUser)
                .shouldHave(statusCode(201))
                .asPojo(UserInfoResponse.class);

            bearerToken = response.accessToken(); // теперь токен сохраняется в поле
    }




    @Test
    @Epic("Аутентификация")
    @Feature("Создание пользователя")
    @Story("Создание нового пользователя")
    @DisplayName("Успешная создание нового пользователя")
    @Severity(SeverityLevel.CRITICAL)
    public void newUserCreated() {



        UserPayloads user = new UserPayloads()
                .email(email)
                .fullName(fullName)
                .password(password)
                .banned(false)
                .verified(true);

        authService.createUser(user, bearerToken)
                .shouldHave(statusCode(201))
                .shouldHave(bodyField("verified", equalTo(true)))
                .shouldHave(bodyField("roles", not(empty())));

    }

    @Test
    @Epic("Аутентификация")
    @Feature("Удаление пользователя")
    @Story("Удаление созданного пользователя")
    @DisplayName("Успешное удаление пользователя")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteUser() {

        UserPayloads user = new UserPayloads()
                .email(email)
                .fullName(fullName)
                .password(password)
                .banned(false)
                .verified(true);

        authService.createUser(user, bearerToken)
                .shouldHave(statusCode(201))
                .shouldHave(bodyField("verified", equalTo(true)))
                .shouldHave(bodyField("roles", not(empty())));

        String userID = DBUtils.getUserIdByEmail(email);

        authService.deleteUser(bearerToken, userID)
                .shouldHave(statusCode(200));


    }

    @Test
    @Epic("Аутентификация")
    @Feature("Обновление пользователя")
    @Story("Обновление созданного пользователя")
    @DisplayName("Успешное обновление пользователя")
    @Severity(SeverityLevel.CRITICAL)
    public void patchUser() {

        UserPayloads user = new UserPayloads()
                .email(email)
                .fullName(fullName)
                .password(password)
                .banned(false)
                .verified(true);

        UserPayloads patchUser = new UserPayloads()
                .fullName("Новое Значения Пользователя")
                .verified(false)
                .banned(true);

        authService.createUser(user, bearerToken)
                .shouldHave(statusCode(201))
                .shouldHave(bodyField("verified", equalTo(true)))
                .shouldHave(bodyField("roles", not(empty())));

        String userID = DBUtils.getUserIdByEmail(email);

        authService.patchUser(patchUser, bearerToken, userID)
                .shouldHave(statusCode(200));


    }

}
