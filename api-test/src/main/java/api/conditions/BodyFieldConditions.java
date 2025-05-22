package api.conditions;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.notNullValue;

@RequiredArgsConstructor
public class BodyFieldConditions implements Condition{

    private final String jsonPath;
    private final Matcher matcher;

    @Override
    @Step("{this}")
    public void check(Response response) {
        response.then().assertThat().body(jsonPath, matcher);

    }

    @Override
    public String toString() {
        return "Проверяем: тело содержит поле \"" + jsonPath + "\" с условием \"" + matcher + "\"";
    }
}
