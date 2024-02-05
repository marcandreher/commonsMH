package marcandreher.commons.Input.Commands;

import java.util.Scanner;

import marcandreher.commons.Commons.Flogger;
import marcandreher.commons.Input.Command;
import marcandreher.commons.Input.CommandHandler;
import marcandreher.commons.Utils.Color;

public class Help implements Command {

    private static final int PAGE_SIZE = 5;

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

    @Override
    public void executeAction(String[] args, Flogger logger) {
        logger.log(Flogger.Prefix.INFO, "Available Commands:", 0);
        int totalPages = (int) Math.ceil(CommandHandler.initializedCommands.size() / (double) PAGE_SIZE);
        int currentPage = 1;
        int startIndex = (currentPage - 1) * PAGE_SIZE;
        int endIndex = Math.min(startIndex + PAGE_SIZE, CommandHandler.initializedCommands.size());
        Scanner scanner = null;
        while (startIndex < endIndex) {
            for (int i = startIndex; i < endIndex; i++) {
                Command c = CommandHandler.initializedCommands.get(i);
                logger.log(Flogger.Prefix.INFO, Color.GREEN + c.getName() + Color.RESET + " - " + c.getDescription(), 0);
            }
           
            if (currentPage < totalPages) {
                logger.log(Flogger.Prefix.INFO, "Press Enter to view next page", 0);
                // Wait for user input to continue to the next page
                // You can add your own logic here to handle user input
                 scanner = new Scanner(System.in);
                scanner.nextLine();
            }

            currentPage++;
            startIndex = (currentPage - 1) * PAGE_SIZE;
            endIndex = Math.min(startIndex + PAGE_SIZE, CommandHandler.initializedCommands.size());
        }
    }
 
}
