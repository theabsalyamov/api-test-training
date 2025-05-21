package api.conditions;

import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HeaderConditions implements Condition{

    private final String name;
    private final String expectedValue;

    @Override
    public void check(Response response) {
        response.then().header(name, expectedValue);
    }

}
