package api.services;


import api.assertions.AssertableResponse;
import api.payloads.AuthPayloads;

//Сюда выводим логику по работе с эндпоинтами auth
public class AuthApiService extends ApiService {

//    Возвращая Response, ты даёшь возможность вызывающему коду (например, тесту) проверить статус, тело и другие детали ответа.
    // Response обрарачиваем в свой отедльный класс
    public AssertableResponse registryUser (AuthPayloads user){

        return new AssertableResponse( setUp()
                .body(user)// Так же задаем праметры для тела
                .when()// Выполнение запроса
                .post("register"));// Опеределяем метод запроса

    }

}
