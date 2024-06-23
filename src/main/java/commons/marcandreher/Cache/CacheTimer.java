package commons.marcandreher.Cache;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import commons.marcandreher.Cache.Action.Action;
import commons.marcandreher.Cache.Action.DatabaseAction;
import commons.marcandreher.Commons.Flogger;
import commons.marcandreher.Commons.Flogger.Prefix;
import commons.marcandreher.Utils.Color;

public class CacheTimer {


    public ArrayList<Action> actionList = new ArrayList<>();

    private int period;

    private boolean isRunning = false;
    private TimeUnit timeUnit;

    public CacheTimer(int period, int poolSize, TimeUnit timeUnit) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(poolSize);
        this.period = period;
        this.timeUnit = timeUnit;
        scheduler.scheduleAtFixedRate(() -> {
            runUpdate(Flogger.instance);
        }, 0, period, timeUnit);
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void addAction(Action action) {
        actionList.add(action);
        TimerRegistry.registerTimer(this, period, timeUnit, action.getClass().getName());
    }

    public void runUpdate(Flogger logger) {
        logger.log(Prefix.INFO, "Updating actions | " + Color.GREEN + actionList.size() + " running again in " + period + "m" + Color.RESET, 10);
       
        for (int i = 0; i < actionList.size(); i++) {
            isRunning = true;
            Action ac = actionList.get(i);
            ac.executeAction(logger);

            if (ac instanceof DatabaseAction) {
                DatabaseAction acdb = (DatabaseAction) ac;
                acdb.mysql.close();
            }
            isRunning = false;
        }
  
    }
    
}
