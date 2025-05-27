package cinescope.api.filters;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true)
public class MoviesFilter {
    private int pageSize;
    private int page;
    private String createdAt; // asc / desc

    private int minPrice;
    private int maxPrice;
    private String locations;
    private boolean published;
}
