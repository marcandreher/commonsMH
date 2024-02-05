package marcandreher.commons.Input.Commands;

import marcandreher.commons.Commons.Flogger;
import marcandreher.commons.Input.Command;
import marcandreher.commons.Utils.Color;

public class Logger implements Command {

    @Override
    public void executeAction(String[] args, Flogger logger) {
        if(args.length < 2) {
            logger.log(Flogger.Prefix.INFO, getAlias(), 0);
            return;
        }

        if (!args[1].matches("\\d+")) {
            logger.log(Flogger.Prefix.ERROR, "Invalid log level. Please provide an integer.", 0);
            return;
        }

        short level = Short.parseShort(args[1]);
        logger.setLogLevel(level);
        logger.log(Flogger.Prefix.INFO, "Log level set to " + level, 0);
    }

    @Override
    public String getAlias() {
        return "logger <" + Color.GREEN + "int" + Color.RESET + ":level>";
    }

    @Override
    public String getDescription() {
        return "Set the log level";
    }

    @Override
    public String getName() {
        return "logger";
    }
    
}
