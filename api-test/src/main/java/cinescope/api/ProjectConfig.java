package cinescope.api;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;


@Config.Sources("classpath:config.properties")
public interface ProjectConfig extends Config {


    String authBaseUrl();

    String adminUserName();

    String adminUserPassword();

    String moviesBaseUrl();

    String baseUrl();

    @DefaultValue("ru")
    String locale();

    @DefaultValue("false")
    boolean logging();
}


