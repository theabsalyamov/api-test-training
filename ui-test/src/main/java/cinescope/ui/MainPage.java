package cinescope.ui;

import com.codeborne.selenide.Selenide;

public class MainPage {
    public static MainPage open() {

        Selenide.open("/");

        return new MainPage();

    }

    public void loginAs(String name, String password) {
    }
}
