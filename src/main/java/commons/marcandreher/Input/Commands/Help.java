package commons.marcandreher.Input.Commands;

import java.util.List;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;

import commons.marcandreher.Input.Command;
import commons.marcandreher.Input.ExtendedCommand;

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

    private static final int PAGE_SIZE = 10;

    public static void executeAction(List<Command> commands, Terminal terminal) {
        try {
            LineReader reader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .option(LineReader.Option.ERASE_LINE_ON_FINISH, true)
                    .option(LineReader.Option.ERASE_LINE_ON_FINISH, true)
                    .parser(new DefaultParser())
                    .build();

            int totalPages = (int) Math.ceil(commands.size() / (double) PAGE_SIZE);
            int currentPage = 1;
            int startIndex = 0;

            while (startIndex < commands.size()) {
                int endIndex = Math.min(startIndex + PAGE_SIZE, commands.size());
                terminal.puts(InfoCmp.Capability.clear_screen);
                terminal.writer().println("Page " + currentPage + "/" + totalPages);
                for (int i = startIndex; i < endIndex; i++) {
                    Command c = commands.get(i);
                    terminal.writer().println(c.getName() + " - " + c.getDescription());
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
