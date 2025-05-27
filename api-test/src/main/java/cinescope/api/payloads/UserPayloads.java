package cinescope.api.payloads;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(fluent = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPayloads{

	@JsonProperty("password")
	private String password;

	@JsonProperty("verified")
	private boolean verified;

	@JsonProperty("fullName")
	private String fullName;

	@JsonProperty("banned")
	private boolean banned;

	@JsonProperty("email")
	private String email;

}