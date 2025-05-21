package api.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(fluent = true) // Позволяет вызывать сеттеры и геттеры через точку обращаясь через "точку" е этим методам без приставок get и set
public class AuthPayloads{

	@JsonProperty("password")
	private String password;

	@JsonProperty("fullName")
	private String fullName;

	@JsonProperty("passwordRepeat")
	private String passwordRepeat;

	@JsonProperty("email")
	private String email;

}