package commons.marcandreher.Input.Commands;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.DefaultParser;
import org.jline.utils.InfoCmp;

import commons.marcandreher.Commons.Flogger;
import commons.marcandreher.Input.Command;
import commons.marcandreher.Input.CommandHandler;
import commons.marcandreher.Input.ExtendedCommand;
import commons.marcandreher.Utils.Color;

public class Help extends ExtendedCommand {

    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public String getDescription() {
        return "List all Commands";
    }

    @Override
    public String getName() {
        return "help";
    }

    private final int PAGE_SIZE = 5;

    @Override
    public void executeAction(String[] args, Flogger logger) {
        try {
            LineReader reader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .option(LineReader.Option.ERASE_LINE_ON_FINISH, true)
                    .option(LineReader.Option.ERASE_LINE_ON_FINISH, true)
                    .parser(new DefaultParser())
                    .build();

            int totalPages = (int) Math.ceil(CommandHandler.initializedCommands.size() / (double) PAGE_SIZE);
            int currentPage = 1;
            int startIndex = 0;

            while (startIndex < CommandHandler.initializedCommands.size()) {
                int endIndex = Math.min(startIndex + PAGE_SIZE, CommandHandler.initializedCommands.size());
                terminal.puts(InfoCmp.Capability.clear_screen);
                terminal.writer().println("█►\tCommonsMH Help " + Color.GREEN + "v" + CommandHandler.VERSION + Color.RESET + "\t◄█");
                terminal.writer().println("■\tPage " + Color.GREEN + currentPage + Color.RESET + "/" + Color.GREEN + totalPages + Color.RESET);
                for (int i = startIndex; i < endIndex; i++) {
                    Command c = CommandHandler.initializedCommands.get(i);
                    terminal.writer().println(Color.WHITE_BOLD_BRIGHT + c.getName() + Color.RESET + " - "  +  c.getDescription());
                    if(c.getAlias() != null)
                    terminal.writer().println("\t└ " + c.getAlias());
                }
                terminal.flush();

                if (currentPage < totalPages) {
                    terminal.writer().println("Press Enter to view next page");
                    terminal.flush();
                }

                String line = reader.readLine();
                if (line == null || line.equalsIgnoreCase("exit")) {
                    break;
                }

                currentPage++;
                startIndex += PAGE_SIZE;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
