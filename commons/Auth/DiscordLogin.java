package marcandreher.commons.Auth;

import dev.coly.discordoauth2.DiscordAPI;
import dev.coly.discordoauth2.DiscordOAuth2;
import dev.coly.discordoauth2.objects.Tokens;
import dev.coly.discordoauth2.objects.User;
import marcandreher.commons.Commons.Flogger;
import marcandreher.commons.Commons.Flogger.Prefix;
import marcandreher.commons.Utils.StringUtils;
import spark.Response;
import spark.Route;
import spark.Spark;

public class DiscordLogin {

    public static String disUrl = "";
    public static DiscordOAuth2 auth = null;
    private Flogger logger;

    public DiscordLogin(String discordAPIID, String discordAPISecret, String domain, String domainRoute,
            Flogger logger) {
        this.logger = logger;

        auth = new DiscordOAuth2(
                discordAPIID,
                discordAPISecret,
                domain + domainRoute,
                DiscordOAuth2.Scope.EMAIL,
                DiscordOAuth2.Scope.IDENTIFY);
        disUrl = auth.getAuthorizationUrl(StringUtils.generateRandomString(5));
    }

    public void registerDiscordRoute(String route, String redirect, DiscordLoginHandler handler) {
        Spark.get("/discord/login", new Route() {
            @Override
            public Object handle(spark.Request request, Response response) {
                User u = null;
                try {
                    Tokens tokens = auth.getTokens(request.queryParams("code"));
                    u = DiscordAPI.getUser(tokens.getAccessToken());
                    logger.log(Prefix.INFO, "-> Discord Login", 3);
                } catch (Exception e) {
                    logger.log(Prefix.ERROR, "Discord login failed", 0);
                    return null;
                }

                handler.handleDiscordLogin(u);

                response.redirect(redirect);
                return null;

            }
        });
    }

}
