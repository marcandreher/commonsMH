package commons.marcandreher.Auth;

import commons.marcandreher.Utils.StringUtils;
import dev.coly.discordoauth2.DiscordOAuth2;

public class DiscordLogin {

    public static String disUrl = "";
    public DiscordOAuth2 auth = null;

    public DiscordLogin(String discordAPIID, String discordAPISecret, String domain, String domainRoute) {
        auth = new DiscordOAuth2(
                discordAPIID,
                discordAPISecret,
                domain + domainRoute,
                DiscordOAuth2.Scope.EMAIL,
                DiscordOAuth2.Scope.IDENTIFY);
        disUrl = auth.getAuthorizationUrl(StringUtils.generateRandomString(5));
    }


}
