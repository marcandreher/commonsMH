package commons.marcandreher.Auth;

import commons.marcandreher.Commons.Flogger;
import commons.marcandreher.Commons.MySQL;
import commons.marcandreher.Utils.SteamUser;
import spark.Request;
import spark.Response;


public interface SteamLoginHandler {


    public String handleDiscordLogin(SteamUser user, Request request, Response response, MySQL mysql, Flogger logger);
    
}

