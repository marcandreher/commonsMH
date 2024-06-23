package commons.marcandreher.Input;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import commons.marcandreher.Commons.Flogger;
import commons.marcandreher.Commons.Flogger.Prefix;
import commons.marcandreher.Exceptions.CommandAlreadyRegisteredException;
import commons.marcandreher.Input.Commands.Help;
import commons.marcandreher.Input.Commands.Logger;
import commons.marcandreher.Input.Commands.Routes;
import commons.marcandreher.Input.Commands.SQL;
import commons.marcandreher.Input.Commands.Shutdown;
import commons.marcandreher.Input.Commands.TestRoute;
import commons.marcandreher.Input.Commands.ThreadCheck;
import commons.marcandreher.Input.Commands.Timers;

public class CommandHandler {
    private Flogger logger;
    public static List<Command> initializedCommands = new ArrayList<>();
    public static final String VERSION = "2.1"; 
    public static Terminal terminal = null;

    public CommandHandler(Flogger logger) {
        this.logger = logger;
    }

    public void initialize() {
        registerCommand(new Logger());
        registerCommand(new ThreadCheck());
        registerCommand(new Shutdown());
        registerCommand(new SQL());
        registerCommand(new Help());
        registerCommand(new TestRoute());
        registerCommand(new Routes());
        registerCommand(new Timers());
        readUserInput();
    }

    private void readUserInput() {

        try {
            terminal = TerminalBuilder.builder().system(true).build();
            LineReader reader = LineReaderBuilder.builder()
                            .history(new DefaultHistory())
                            .terminal(terminal)
                            .build();

            logger.log(Prefix.INFO, "CommandHandler v" + VERSION + " | Type help", 1);

            while (true) {
                try {
                    String input = reader.readLine("admin@commonsmh:~$ ");

                    if (input == null)
                        break;

                    String[] argsCmd = input.split(" ");

                    Boolean foundCmd = false;
                    for (Command cmd : initializedCommands) {
                        if (argsCmd[0].equalsIgnoreCase(cmd.getName())) {

                            if(cmd instanceof ExtendedCommand extCmd) extCmd.terminal = terminal;

                            cmd.executeAction(argsCmd, logger);

                            if(cmd instanceof DatabaseCommand dbCmd) if(dbCmd.mysql != null) dbCmd.mysql.close();

                            foundCmd = true;
                            break;
                        }
                        ((DefaultHistory) reader.getHistory()).add(argsCmd[0]);
                    }

                    if (!foundCmd) {
                        logger.log(Prefix.INFO, "-> No Command found | Type help", 1);
                    }
                } catch (UserInterruptException e) {
                    continue;
                } catch (Exception e) {
                    logger.error(e);
                }
            }
        } catch (IOException e) {
            logger.error(e);
        }
    }



    public void registerCommand(Command cmd) {
        if(initializedCommands.stream().noneMatch(c -> c.getName().equalsIgnoreCase(cmd.getName()))) {
            initializedCommands.add(cmd);
        } else {
            logger.error(new CommandAlreadyRegisteredException("Command " + cmd.getName() + " is already registered!"));
        }
       
    }

    public void shutdown() {
        System.exit(0);
    }
}
