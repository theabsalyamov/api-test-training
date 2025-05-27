package cinescope.api.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@Setter
public class LoginPayloads{

	@JsonProperty("password")
	private String password;

	@JsonProperty("email")
	private String email;

}