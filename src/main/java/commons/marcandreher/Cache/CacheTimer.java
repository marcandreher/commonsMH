package commons.marcandreher.Cache;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import commons.marcandreher.Cache.Action.Action;
import commons.marcandreher.Cache.Action.DatabaseAction;
import commons.marcandreher.Commons.Flogger;
import commons.marcandreher.Utils.Color;

public class CacheTimer {


    public ArrayList<Action> actionList = new ArrayList<>();

    private int period;

    public CacheTimer(int period, int poolSize, Flogger logger) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(poolSize);
        this.period = period;
        scheduler.scheduleAtFixedRate(() -> {
            runUpdate(logger);
        }, 0, period, TimeUnit.MINUTES);
    }

    public void addAction(Action action) {
        actionList.add(action);
    }

    public void runUpdate(Flogger logger) {
        logger.log("  -> Updating actions | " + Color.GREEN +actionList.size() + " running again in " + period+"m" + Color.RESET, 3);

        for(int i = 0; i < actionList.size(); i++) {
            Action ac = actionList.get(i);
            ac.executeAction(logger);

            if(ac instanceof DatabaseAction) {
                DatabaseAction acdb = (DatabaseAction) ac;
                acdb.mysql.close();
            }
        }
    }
    
}
