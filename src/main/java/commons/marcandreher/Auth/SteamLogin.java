package commons.marcandreher.Auth;

public class SteamLogin {

    public static String stUrl = "";
    private SteamLoginHelper steamLogin;
    public String steamApiKey = "";

    public SteamLogin(String domain, String steamApiKey, String route) {
        steamLogin = new SteamLoginHelper();
        this.steamApiKey = steamApiKey;
        stUrl = steamLogin.login(domain + route);
    }

    public SteamLoginHelper getSteamLogin() {
        return steamLogin;
    }
    
}
