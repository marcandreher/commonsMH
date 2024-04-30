package commons.marcandreher.Input;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import commons.marcandreher.Commons.Flogger;
import commons.marcandreher.Commons.Flogger.Prefix;
import commons.marcandreher.Input.Commands.Help;
import commons.marcandreher.Input.Commands.Logger;
import commons.marcandreher.Input.Commands.SQL;
import commons.marcandreher.Input.Commands.Shutdown;
import commons.marcandreher.Input.Commands.TestRoute;
import commons.marcandreher.Input.Commands.ThreadCheck;

public class CommandHandler extends Thread {

    private Flogger logger;

    public CommandHandler(Flogger llogger) {
        this.logger = llogger;

    }

    public static List<Command> initializedCommands = new ArrayList<>();

    public void run() {
        Terminal terminal = null;
        try {
            terminal = TerminalBuilder.builder().system(true).build();
        } catch (IOException e) {
            Flogger.instance.error(e);
            return;
        }
        LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();
        logger.log(Prefix.INFO, "CommandHandler v1.9 | Type help", 1);
        while (true) {
            try {
                String input = reader.readLine("admin@commonsmh:~$ ");

                if (input == null)
                    break;

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
            } catch (UserInterruptException e) {
                continue;
            } catch (Exception e) {
                logger.error(e);
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
        registerCommand(new TestRoute());

        CommandHandler commandHandlerThread = new CommandHandler(logger);
        commandHandlerThread.setName("CommandHandler");
        commandHandlerThread.start();
    }

    public void registerCommand(Command cmd) {
        initializedCommands.add(cmd);
    }

}
