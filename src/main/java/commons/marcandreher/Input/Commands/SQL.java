package commons.marcandreher.Input.Commands;

import commons.marcandreher.Commons.Database;
import commons.marcandreher.Commons.Flogger;
import commons.marcandreher.Commons.MySQL;
import commons.marcandreher.Commons.Flogger.Prefix;
import commons.marcandreher.Input.Command;
import commons.marcandreher.Utils.Color;

public class SQL implements Command {

    @Override
    public void executeAction(String[] args, Flogger logger) {
        if(args.length < 2) {
                System.out.println(Prefix.INFO + getAlias());
                return;
        }


        switch(args[1]) {
            case "consize":
                System.out.println(Prefix.INFO + "Consize is " + Database.currentConnections);
                break;
            case "log":
                if(args.length <= 2) {
                    System.out.println(Prefix.INFO + getAlias());
                    return;
                }
                int level = Integer.parseInt(args[2]);
                MySQL.LOGLEVEL = level;
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
