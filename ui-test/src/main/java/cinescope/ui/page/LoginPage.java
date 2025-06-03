package cinescope.ui.page;

import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    public static LoginPage open() {

        Selenide.open("/login");

        return new LoginPage();

    }

    public void loginAs(String name, String password) {

        $("[data-qa-id='login_email_input']").setValue(name);
        $("[data-qa-id='login_password_input']").setValue(password);
        $("[data-qa-id='login_submit_button']").click();


    }
}
