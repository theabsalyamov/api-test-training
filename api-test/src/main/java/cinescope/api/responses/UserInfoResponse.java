package cinescope.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Accessors(fluent = true)
@Setter
@Getter
public class UserInfoResponse {

	@JsonProperty("expiresIn")
	private long expiresIn;

	@JsonProperty("accessToken")
	private String accessToken;

	@JsonProperty("user")
	private UserResponse user;

	@JsonProperty("refreshToken")
	private String refreshToken;
}