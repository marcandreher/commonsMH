package commons.marcandreher.Input;

import java.util.ArrayList;
import java.util.List;

import commons.marcandreher.Commons.Flogger;
import commons.marcandreher.Commons.Flogger.Prefix;
import commons.marcandreher.Input.Commands.Help;
import commons.marcandreher.Input.Commands.Logger;
import commons.marcandreher.Input.Commands.SQL;
import commons.marcandreher.Input.Commands.Shutdown;
import commons.marcandreher.Input.Commands.ThreadCheck;




public class CommandHandler extends Thread {

    private Flogger logger;

    public CommandHandler(Flogger llogger) {
        this.logger = llogger;
    }

    public static List<Command> initializedCommands = new ArrayList<>();

    public void run() {
        logger.log(Prefix.INFO,  "CommandHandler v1.9 | Type help", 1);
        while (true) {
            // Read user input
            System.out.print("admin@commonsmh:~$ ");
            String input = System.console().readLine();
    
            if (input == null) continue;
    
            // Split input into command and arguments
            String[] argsCmd = input.split(" ");
    
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
    
            // Move cursor to the next line
            System.out.println();
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
