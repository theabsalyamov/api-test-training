package cinescope.api.services;

import cinescope.api.ProjectConfig;
import cinescope.api.assertions.AssertableResponse;
import cinescope.api.filters.MoviesFilter;
import cinescope.api.payloads.MoviesPayloads;
import cinescope.api.payloads.RegisterPayloads;
import io.qameta.allure.Step;
import org.aeonbits.owner.ConfigFactory;

import java.util.Map;

public class MoviesApiService extends ApiService{

    public MoviesApiService() {
        this.baseUrl = ConfigFactory.create(ProjectConfig.class, System.getProperties()).moviesBaseUrl();
    }

    @Step("Создание фильма")
    public AssertableResponse createNewMovies (MoviesPayloads movie, String token){

        return new AssertableResponse( setUp()
                .header("Authorization", "Bearer " + token)
                .body(movie)// Так же задаем праметры для тела
                .when()// Выполнение запроса
                .post("movies"));// Опеределяем метод запроса

    }

    @Step("Получаем список фильмов с фильтрами")
    public AssertableResponse getFilteredMovies(MoviesFilter filter, String token) {
        return new AssertableResponse(
                setUp()
                        .header("Authorization", "Bearer " + token)
                        .queryParam("pageSize", filter.pageSize())
                        .queryParam("page", filter.page())
                        .queryParam("createdAt", filter.createdAt())
                        .queryParam("maxPrice", filter.maxPrice())
                        .queryParam("minPrice", filter.minPrice())
                        .queryParam("locations", filter.locations())
                        .queryParam("published", filter.published())
                        .when()
                        .get("movies"));

    }

}
