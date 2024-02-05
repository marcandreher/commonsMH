package commons.marcandreher.Cache;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import commons.marcandreher.Cache.Action.Action;
import commons.marcandreher.Commons.Flogger;
import commons.marcandreher.Commons.Flogger.Prefix;





public class CacheTimer {


    public ArrayList<Action> actionList = new ArrayList<>();

    public CacheTimer(int period, int poolSize, Flogger logger) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(poolSize);

        scheduler.scheduleAtFixedRate(() -> {
            runUpdate(logger);
        }, 0, period, TimeUnit.MINUTES);
    }

    public void addAction(Action action) {
        actionList.add(action);
    }

    public void runUpdate(Flogger logger) {
        logger.log(Prefix.INFO, "-> Updating actions | " +actionList.size(), 3);

        for(int i = 0; i < actionList.size(); i++) {
            Action ac = actionList.get(i);
            ac.executeAction(logger);
        }
    }
    
}
