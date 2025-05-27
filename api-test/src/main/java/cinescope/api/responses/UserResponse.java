package cinescope.api.responses;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Accessors(fluent = true)
@Setter
@Getter
public class UserResponse {

	@JsonProperty("roles")
	private List<String> roles;

	@JsonProperty("fullName")
	private String fullName;

	@JsonProperty("id")
	private String id;

	@JsonProperty("email")
	private String email;
}