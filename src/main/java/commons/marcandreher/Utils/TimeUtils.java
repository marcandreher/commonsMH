package commons.marcandreher.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import commons.marcandreher.Commons.Flogger;
import commons.marcandreher.Commons.Flogger.Prefix;

public class TimeUtils {
    public static long calculateTimeDifference(String time1, String time2) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = format.parse(time1);
            date2 = format.parse(time2);
        } catch (Exception e) {
            Flogger.instance.log(Prefix.ERROR, "Failed to convert TimeDiffString " + time1 + "," + time2, 0);
            return 0;
        }
        
        
        long difference = date1.getTime() - date2.getTime();
        return difference;
    }

    public static String formatTime(long milliseconds) {
        long seconds = milliseconds / 1000;
        long hours = seconds / 3600;
        long remainderMinutes = (seconds % 3600) / 60;
        long remainderSeconds = (seconds % 3600) % 60;

        return String.format("%02d:%02d:%02d", hours, remainderMinutes, remainderSeconds);
    }

    public static int compareTimes(String time1, String time2) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date1 = format.parse(time1);
        Date date2 = format.parse(time2);
        
        return date1.compareTo(date2);
    }
}
