package cinescope.ui.tests;


import cinescope.api.ProjectConfig;
import cinescope.api.payloads.RegisterPayloads;
import cinescope.api.services.AuthApiService;
import cinescope.api.utils.PasswordGenerator;
import cinescope.ui.page.LoginPage;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.Test;

import static cinescope.api.conditions.Conditions.statusCode;
import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

import net.datafaker.Faker;

import java.util.Locale;


public class TestLogin extends BaseUITest{

    private final AuthApiService authService = new AuthApiService();
    private String fullName;
    private String email;
    private String password;
    ProjectConfig config = ConfigFactory.create(ProjectConfig.class, System.getProperties());
    private final Faker faker = new Faker(new Locale(config.locale()));


    @Test
    public void userCanLoginWithValidCredentials (){

        fullName = faker.name().fullName();
        email = faker.internet().emailAddress();
        password = PasswordGenerator.generateValidPassword();

        RegisterPayloads user = new RegisterPayloads()
                .fullName(fullName)
                .email(email)
                .password(password)
                .passwordRepeat(password);


        authService.registryUser(user)
                .shouldHave(statusCode(201));


        LoginPage.open()
                .loginAs(user.email(), user.password());


        $("[role='status']").should(appear).shouldHave(text("Вы вошли в аккаунт"));

    }

}
