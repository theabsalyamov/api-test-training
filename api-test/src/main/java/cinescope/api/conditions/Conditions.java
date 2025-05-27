package cinescope.api.conditions;

import lombok.experimental.UtilityClass;
import org.hamcrest.Matcher;

@UtilityClass
public class Conditions {
    public StatusCodeCondition statusCode (int code){
        return new StatusCodeCondition(code);
    }

    public BodyFieldConditions bodyField (String jsonPath, Matcher matcher){
        return new BodyFieldConditions(jsonPath, matcher);
    }

    public HeaderConditions header (String name, String expectedValue){
        return new HeaderConditions(name, expectedValue);
    }
}
