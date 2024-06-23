package commons.marcandreher.Cache;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class TimerRegistry {

    public static ArrayList<TimerEntry> timers = new ArrayList<>();

    public static void registerTimer(CacheTimer timer, int duration, TimeUnit timeUnit, String className) {
        TimerEntry entry = new TimerEntry();
        entry.timer = timer;
        entry.duration = duration;
        entry.timeUnit = timeUnit;
        entry.className = className;
        timers.add(entry);
    }

    public static class TimerEntry {
        public CacheTimer timer;
        public int duration;
        public TimeUnit timeUnit;
        public String className;

        public TimerEntry() {
        
        }
    }
    
}
