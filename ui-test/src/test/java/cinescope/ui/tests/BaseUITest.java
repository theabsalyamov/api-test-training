package cinescope.ui.tests;

import cinescope.api.ProjectConfig;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.restassured.RestAssured;
import net.datafaker.Faker;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;

import java.util.Locale;


public abstract class BaseUITest {


    @BeforeAll
    public static void setUp() {
        ProjectConfig config = ConfigFactory.create(ProjectConfig.class, System.getProperties());
        RestAssured.baseURI = config.authBaseUrl(); // для API
        Configuration.baseUrl = config.baseUrl(); // для UI
    }

    protected <T> T at(Class<T> pageClass){
        return Selenide.page(pageClass);
    }

}

