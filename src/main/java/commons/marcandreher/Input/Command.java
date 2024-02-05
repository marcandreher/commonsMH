package commons.marcandreher.Input;

import commons.marcandreher.Commons.Flogger;

public interface Command {


    public void executeAction(String[] args, Flogger logger);

    public String getAlias();

    public String getDescription();

    public String getName();
    
}
