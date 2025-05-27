package cinescope.api.conditions;

import io.restassured.response.Response;

public interface Condition {
    default void check(Response response){}
}
