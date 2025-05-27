package tests.Movies;

import cinescope.api.ProjectConfig;
import cinescope.api.filters.MoviesFilter;
import cinescope.api.payloads.LoginPayloads;
import cinescope.api.payloads.MoviesPayloads;
import cinescope.api.payloads.RegisterPayloads;
import cinescope.api.responses.UserInfoResponse;
import cinescope.api.services.AuthApiService;
import cinescope.api.services.MoviesApiService;
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


public class MoviesTests {

    private final AuthApiService authService = new AuthApiService();
    private final MoviesApiService moviesService = new MoviesApiService();
    private static Faker faker;
    private static String adminUserName;
    private static String adminUserPassword;
    private String bearerToken;
    private String movieName;
    private String imageUrl;
    private int price;
    private String description;
    private int genreId;


    @BeforeAll
    public static void setUp(){

        ProjectConfig config = ConfigFactory.create(ProjectConfig.class, System.getProperties());
        faker = new Faker(new Locale(config.locale()));

        adminUserName = config.adminUserName();
        adminUserPassword = config.adminUserPassword();

    }

    @BeforeEach
    public void prepareDataAndToken(){

        LoginPayloads loginUser = new LoginPayloads()
                .email(adminUserName)
                .password(adminUserPassword);

        UserInfoResponse response = authService.loginUser(loginUser)
                .shouldHave(statusCode(201))
                .asPojo(UserInfoResponse.class);

        bearerToken = response.accessToken();

        movieName = faker.movie().name();
        imageUrl = "https://picsum.photos/seed/" + faker.random().hex(8) + "/300/200";
        price = faker.number().numberBetween(1, 1000);
        description = faker.lorem().sentence();
        genreId = faker.number().numberBetween(1, 100);


    }

    @Test
    @Epic("Фильмы")
    @Feature("Создание фильма")
    @Story("Создание нового фильма")
    @DisplayName("Успешное создание нового фильма")
    @Severity(SeverityLevel.CRITICAL)
    public void newMovieCreated() {

        MoviesPayloads movie = new MoviesPayloads()
                .name(movieName)
                .imageUrl(imageUrl)
                .price(price)
                .description(description)
                .location("MSK")
                .published(true)
                .genreId(genreId);

        moviesService.createNewMovies(movie, bearerToken)
                .shouldHave(statusCode(201))
                .shouldHave(bodyField("id", notNullValue()));

    }

    @Test
    @Epic("Фильмы")
    @Feature("Получение фильмов")
    @Story("Получение списка фильмов")
    @DisplayName("Успешное получение фильмов")
    @Severity(SeverityLevel.CRITICAL)
    public void getMovies() {

        MoviesFilter filter = new MoviesFilter()
                .pageSize(1)
                .page(1)
                .createdAt("asc")
                .published(true)
                .minPrice(1)
                .maxPrice(100)
                .locations("SPB,MSK");

        moviesService.getFilteredMovies(filter, bearerToken)
                .shouldHave(statusCode(200));

    }

}
