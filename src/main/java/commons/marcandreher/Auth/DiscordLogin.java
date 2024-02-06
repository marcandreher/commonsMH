package commons.marcandreher.Auth;

import commons.marcandreher.Commons.Flogger;
import commons.marcandreher.Commons.Flogger.Prefix;
import commons.marcandreher.Utils.StringUtils;
import dev.coly.discordoauth2.DiscordAPI;
import dev.coly.discordoauth2.DiscordOAuth2;
import dev.coly.discordoauth2.objects.Tokens;
import dev.coly.discordoauth2.objects.User;
import spark.Response;
import spark.Route;
import spark.Spark;

public class DiscordLogin {

    public static String disUrl = "";
    public DiscordOAuth2 auth = null;
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


}
