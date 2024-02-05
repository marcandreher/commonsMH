package commons.marcandreher.Input.Commands;

import commons.marcandreher.Commons.Flogger;
import commons.marcandreher.Commons.Flogger.Prefix;
import commons.marcandreher.Input.Command;
import commons.marcandreher.Utils.Color;

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
