package commons.marcandreher.Auth;

import dev.coly.discordoauth2.objects.User;
import spark.Request;
import spark.Response;


public interface DiscordLoginHandler {

    public void handleDiscordLogin(User u, Request request, Response response);
    
}
