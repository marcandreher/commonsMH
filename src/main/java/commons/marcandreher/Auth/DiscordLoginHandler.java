package commons.marcandreher.Auth;

import commons.marcandreher.Commons.MySQL;
import dev.coly.discordoauth2.objects.User;
import spark.Request;
import spark.Response;


public interface DiscordLoginHandler {

    public String handleDiscordLogin(User u, Request request, Response response, MySQL mysql);
    
}
