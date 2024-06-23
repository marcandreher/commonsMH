package commons.marcandreher.Input.Commands;

import commons.marcandreher.Cache.TimerRegistry;
import commons.marcandreher.Cache.TimerRegistry.TimerEntry;
import commons.marcandreher.Commons.Flogger;
import commons.marcandreher.Commons.Flogger.Prefix;
import commons.marcandreher.Input.Command;

public class Timers implements Command{

    @Override
    public void executeAction(String[] args, Flogger logger) {
        logger.log(Prefix.API, "Currently running timers:");
        for(TimerEntry entry : TimerRegistry.timers) {
            logger.log(Prefix.INFO, entry.className + " | " + entry.duration + " " + entry.timeUnit.toString());
        }
        if(TimerRegistry.timers.isEmpty()) {
            logger.log(Prefix.ERROR, "No timers running");
        }
    }

    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Get information about running cachetimers";
    }

    @Override
    public String getName() {
        return "timers";
    }
    
}
