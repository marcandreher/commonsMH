package commons.marcandreher.Input.Commands;


import commons.marcandreher.Commons.Flogger;
import commons.marcandreher.Commons.WebServer;
import commons.marcandreher.Commons.Flogger.Prefix;
import commons.marcandreher.Input.Command;
import commons.marcandreher.Utils.Color;

public class Webserver implements Command {

    private WebServer webServer;

    @Override
    public void executeAction(String[] args, Flogger logger) {
        logger.log(Prefix.INFO, "Webserver is running on " + Color.GREEN + webServer.getWebIp() + ":" + webServer.getWebPort() + Color.RESET, 0);
        
    }

    public void setWebServer(WebServer webServer) {
        this.webServer = webServer;
    }

    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Return current location and status of the webserver.";
    }

    @Override
    public String getName() {
        return "webserver";
    }
    
}
