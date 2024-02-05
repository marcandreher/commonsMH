package marcandreher.commons.Input;

import java.util.ArrayList;
import java.util.List;

import marcandreher.commons.Commons.Flogger;
import marcandreher.commons.Commons.Flogger.Prefix;
import marcandreher.commons.Input.Commands.Help;
import marcandreher.commons.Input.Commands.Logger;
import marcandreher.commons.Input.Commands.SQL;
import marcandreher.commons.Input.Commands.Shutdown;
import marcandreher.commons.Input.Commands.ThreadCheck;



public class CommandHandler extends Thread {

    private Flogger logger;

    public CommandHandler(Flogger llogger) {
        this.logger = llogger;
    }

    public static List<Command> initializedCommands = new ArrayList<>();

    public void run() {
        logger.log(Prefix.INFO,  "CommandHandler v1.9 | Type help", 1);
        while (true) {
            System.out.print("admin@commonsmh:~$ ");

            // Read user input
            String input = System.console().readLine();

            // Split input into command and arguments
            String[] argsCmd = input.split(" ", 2);

            Boolean foundCmd = false;
            for (Command cmd : initializedCommands) {
                if (argsCmd[0].equalsIgnoreCase(cmd.getName())) {
                    cmd.executeAction(argsCmd, logger);
                    foundCmd = true;
                    break;
                }
            }

            if (!foundCmd) {
                logger.log(Prefix.INFO, "-> No Command found | Type help", 1);
            }
        }
    }

    public void initialize() {
        registerCommand(new Logger());
        registerCommand(new ThreadCheck());
        registerCommand(new Shutdown());
        registerCommand(new SQL());
        registerCommand(new ThreadCheck());
        registerCommand(new Help());

        CommandHandler commandHandlerThread = new CommandHandler(logger);
        commandHandlerThread.setName("CommandHandler");
        commandHandlerThread.start();
    }

    public void registerCommand(Command cmd) {
        initializedCommands.add(cmd);
    }

}
