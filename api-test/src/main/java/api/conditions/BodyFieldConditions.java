package api.conditions;

import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.notNullValue;

@RequiredArgsConstructor
public class BodyFieldConditions implements Condition{

    private final String jsonPath;
    private final Matcher matcher;

    @Override
    public void check(Response response) {
        response.then().assertThat().body(jsonPath, matcher);

    }

    @Override
    public String toString() {
        return "body field [" + jsonPath + "] " + matcher;
    }
}
