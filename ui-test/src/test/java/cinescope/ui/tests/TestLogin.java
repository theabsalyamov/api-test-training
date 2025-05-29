package cinescope.ui.tests;

import cinescope.ui.MainPage;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class TestLogin {

    @Test
    public void userCanLoginWithValidCredentials (){
//        Selenide.open("https://dev-cinescope.t-qa.ru/login");

        MainPage.open()
                .loginAs("", "");

        $("[data-qa-id='login_email_input']").setValue("test-admin@mail.com");
        $("[data-qa-id='login_password_input']").setValue("KcLMmxkJMjBD1");
        $("[data-qa-id='login_submit_button']").click();

        $("h1").shouldHave(text("Welcome"));

    }

}
