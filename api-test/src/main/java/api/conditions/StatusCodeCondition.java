package api.conditions;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatusCodeCondition implements Condition{

    private final int statusCode;

    @Override
    @Step("{this}")
    public void check(Response response) {
        response.then().assertThat().statusCode(statusCode);
    }

    @Override
    public String toString() {
        return "Проверяем: статус равен " + statusCode;
    }
}
