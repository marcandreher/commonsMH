package commons.marcandreher.Input.Commands;

import commons.marcandreher.Commons.Flogger;
import commons.marcandreher.Commons.Router;
import commons.marcandreher.Commons.Flogger.Prefix;
import commons.marcandreher.Commons.Router.RoutePair;
import commons.marcandreher.Input.Command;

public class Routes implements Command {

    @Override
    public void executeAction(String[] args, Flogger logger) {
        if(Router.instance == null) {
            logger.log(Prefix.ERROR, "Router is not initialized!");
            return;
        }

        for (RoutePair pair : Router.instance.getRoutes()) {
            logger.log(Prefix.INFO, "[" + pair.getRequestType() + "] " + pair.getRoute());
        }
    }

    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Shows all available routes.";
    }

    @Override
    public String getName() {
        return "routes";
    }
    
}
