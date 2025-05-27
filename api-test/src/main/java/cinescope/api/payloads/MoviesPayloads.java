package cinescope.api.payloads;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoviesPayloads{

	@JsonProperty("genreId")
	private int genreId;

	@JsonProperty("price")
	private int price;

	@JsonProperty("imageUrl")
	private String imageUrl;

	@JsonProperty("name")
	private String name;

	@JsonProperty("description")
	private String description;

	@JsonProperty("location")
	private String location;

	@JsonProperty("published")
	private boolean published;

}