package config;
import org.aeonbits.owner.Config;
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:configAPI.properties"
})
public interface ProjectConfig extends Config{
    @Key("login")
    String login();
    @Key("password")
    String password();
    @Key("authCookie")
    String authCookie();
    @Key("baseUrl")
    String getBaseUrl();
    @Key("baseURI")
    String getBaseUri();
    @Key("remoteUrl")
    String getRemoteUrl();
    @Key("browser")
    String getBrowser();
    @Key("browserSize")
    String getBrowserSize();
    @Key("browserVersion")
    String getBrowserVersion();

}
