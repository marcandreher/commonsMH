package marcandreher.commons.Auth;

import dev.coly.discordoauth2.objects.User;

public interface DiscordLoginHandler {

    public void handleDiscordLogin(User u);
    
}
