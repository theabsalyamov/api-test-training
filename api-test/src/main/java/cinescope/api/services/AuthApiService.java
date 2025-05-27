package cinescope.api.services;


import cinescope.api.ProjectConfig;
import cinescope.api.assertions.AssertableResponse;
import cinescope.api.payloads.LoginPayloads;
import cinescope.api.payloads.RegisterPayloads;
import cinescope.api.payloads.UserPayloads;
import io.qameta.allure.Step;
import org.aeonbits.owner.ConfigFactory;

//Сюда выводим логику по работе с эндпоинтами auth
public class AuthApiService extends ApiService {


    public AuthApiService() {
        this.baseUrl = ConfigFactory.create(ProjectConfig.class, System.getProperties()).authBaseUrl();
    }


    //    Возвращая Response, ты даёшь возможность вызывающему коду (например, тесту) проверить статус, тело и другие детали ответа.
    // Response обрарачиваем в свой отедльный класс
    @Step("Регистрируем пользователя: {user.email}")
    public AssertableResponse registryUser (RegisterPayloads user){

        return new AssertableResponse( setUp()
                .body(user)// Так же задаем праметры для тела
                .when()// Выполнение запроса
                .post("register"));// Опеределяем метод запроса

    }

    @Step("Авторизуем пользователя: {user.email}")
    public AssertableResponse loginUser (LoginPayloads user){

        return new AssertableResponse( setUp()
                .body(user)// Так же задаем праметры для тела
                .when()// Выполнение запроса
                .post("login"));// Опеределяем метод запроса

    }

    @Step("Создаем пользователя: {user.email}")
    public AssertableResponse createUser (UserPayloads user, String token){

        return new AssertableResponse( setUp()
                .header("Authorization", "Bearer " + token)
                .body(user)// Так же задаем праметры для тела
                .when()// Выполнение запроса
                .post("user"));// Опеределяем метод запроса

    }

    @Step("Удаляем пользователя по id")
    public AssertableResponse deleteUser (String token, String userId){

        return new AssertableResponse( setUp()
                .header("Authorization", "Bearer " + token)
                .when()// Выполнение запроса
                .delete("user/" + userId));

    }

    @Step("Изменение пользователя: {user.email}")
    public AssertableResponse patchUser (UserPayloads user, String token, String userId){

        return new AssertableResponse( setUp()
                .header("Authorization", "Bearer " + token)
                .body(user)
                .when()// Выполнение запроса
                .patch("user/" + userId));

    }

}
