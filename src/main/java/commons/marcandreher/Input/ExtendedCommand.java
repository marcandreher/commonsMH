package commons.marcandreher.Input;

import org.jline.terminal.Terminal;

import commons.marcandreher.Commons.Flogger;

public class ExtendedCommand extends DatabaseCommand {

    public Terminal terminal;

    @Override
    public void executeAction(String[] args, Flogger logger) {
        super.executeAction(args, logger);
    }

    @Override
    public String getAlias() {
        throw new UnsupportedOperationException("Unimplemented method 'getAlias'");
    }

    @Override
    public String getDescription() {
        throw new UnsupportedOperationException("Unimplemented method 'getDescription'");
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
    }
    
}
