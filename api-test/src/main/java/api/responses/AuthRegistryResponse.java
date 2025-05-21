package api.responses;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
public class AuthRegistryResponse {

	@JsonProperty("roles")
	private List<String> roles;

	@JsonProperty("verified")
	private boolean verified;

	@JsonProperty("id")
	private String id;

	@JsonProperty("banned")
	private boolean banned;
}