package marcandreher.commons.Input.Commands;

import marcandreher.commons.Commons.Database;
import marcandreher.commons.Commons.Flogger;
import marcandreher.commons.Commons.Flogger.Prefix;
import marcandreher.commons.Input.Command;
import marcandreher.commons.Utils.Color;

public class SQL implements Command {

    @Override
    public void executeAction(String[] args, Flogger logger) {
        if(args.length < 2) {
                System.out.println(Prefix.INFO + getAlias());
                return;
        }


        switch(args[1]) {
            case "consize":
                System.out.println(Prefix.INFO + "Consize is " + Database.getCurrentConnections());
                break;
            case "log":
                if(args.length < 3) {
                    System.out.println(Prefix.INFO + getAlias());
                    return;
                }
                short level = Short.parseShort(args[2]);
                logger.setLogLevel(level);
                System.out.println(Prefix.INFO + "Log level set to " + level);
                break;
            default:
                System.out.println(Prefix.INFO + getAlias());
                break;
        }
    }

    @Override
    public String getAlias() {
         return "sql <" + Color.GREEN + "string" + Color.RESET + ":consize/log>" + "<" + Color.GREEN + "int" + Color.RESET + ":level" + Color.GREEN + "?" + Color.RESET + ">";
    }

    @Override
    public String getDescription() {
        return "Get Info about sql";
    }

    @Override
    public String getName() {
        return "sql";
    }

}
