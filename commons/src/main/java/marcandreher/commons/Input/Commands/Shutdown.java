package marcandreher.commons.Input.Commands;

import marcandreher.commons.Commons.Flogger;
import marcandreher.commons.Commons.Flogger.Prefix;
import marcandreher.commons.Input.Command;
import marcandreher.commons.Utils.Color;

public class Shutdown implements Command {



    @Override
    public String getAlias() {
        return "shutdown <" + Color.GREEN + "string" + Color.RESET + ":now>";
    }

    @Override
    public String getDescription() {
        return "Shutdown the System";
    }

    @Override
    public String getName() {
        return "shutdown";
    }

    @Override
    public void executeAction(String[] args, Flogger logger) {
        if(args.length < 2) {
            logger.log(Prefix.INFO, getAlias(), 0);
            return;
        }

        if(args[1].equalsIgnoreCase("now")) {
            logger.log(Prefix.INFO, "Shutting down...", 0);
            System.exit(0);
        } else {
            logger.log(Prefix.INFO, getAlias(), 0);
        }
    }

}
