package commons.marcandreher.Cache;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import commons.marcandreher.Cache.Action.Action;
import commons.marcandreher.Cache.Action.DatabaseAction;
import commons.marcandreher.Commons.Database;
import commons.marcandreher.Commons.Flogger;
import commons.marcandreher.Commons.Flogger.Prefix;
import commons.marcandreher.Commons.MySQL;
import commons.marcandreher.Utils.Color;

public class CacheTimer {


    public ArrayList<Action> actionList = new ArrayList<>();

    private int period;

    private boolean isRunning = false;
    private TimeUnit timeUnit;

    public CacheTimer(int period, int poolSize, TimeUnit timeUnit) {
        this.period = period;
        this.timeUnit = timeUnit;

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(poolSize);

         Thread delayThread = new Thread(() -> {
            try {
                Thread.sleep(15000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            scheduler.scheduleAtFixedRate(() -> {
                runUpdate(Flogger.instance);
            }, 0, period, timeUnit);
        });

        delayThread.start();
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
        
        MySQL cacheSQL;
        try {
            cacheSQL = Database.getConnection();
        } catch (SQLException e) {
            logger.error(e);
            return;
        }

        for (int i = 0; i < actionList.size(); i++) {

            isRunning = true;

            Action ac = actionList.get(i);
            if (ac instanceof DatabaseAction acdb) acdb.mysql = cacheSQL;
            ac.executeAction(logger);

            isRunning = false;
        }
        
        cacheSQL.close();
  
    }
    
}
