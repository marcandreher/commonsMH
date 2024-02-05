package marcandreher.commons.Utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class TimestampConverter {

    /**
     * Calculates the time difference between the given timestamp and the current time.
     * Returns a string representation of the time difference.
     *
     * @param timestamp The timestamp to calculate the time difference from.
     * @return A string representation of the time difference.
     */
    public static String getTimeDifference(LocalDateTime timestamp) {
        LocalDateTime now = LocalDateTime.now();
        boolean isFuture = timestamp.isAfter(now);
        LocalDateTime baseTime = isFuture ? now : timestamp;
        LocalDateTime targetTime = isFuture ? timestamp : now;

        Period period = Period.between(baseTime.toLocalDate(), targetTime.toLocalDate());
        Duration duration = Duration.between(baseTime.toLocalTime(), targetTime.toLocalTime());

        if (period.isZero() && duration.isZero()) {
            return "now";
        }

        if (period.getYears() > 0) {
            return period.getYears() + (period.getYears() == 1 ? " year ago" : " years ago");
        }

        if (period.getMonths() > 0) {
            return period.getMonths() + (period.getMonths() == 1 ? " month ago" : " months ago");
        }

        if (period.getDays() > 0) {
            return period.getDays() + (period.getDays() == 1 ? " day ago" : " days ago");
        }

        if (duration.toHours() > 0) {
            return duration.toHours() + (duration.toHours() == 1 ? " hour ago" : " hours ago");
        }

        if (duration.toMinutes() > 0) {
            return duration.toMinutes() + (duration.toMinutes() == 1 ? " minute ago" : " minutes ago");
        }

        return "Invalid timestamp";
    }

    /**
     * Calculates the time difference between the given date string and the current time.
     * The date string must be in the format "yyyy-MM-dd HH:mm:ss".
     * Returns a string representation of the time difference.
     *
     * @param date The date string to calculate the time difference from.
     * @return A string representation of the time difference.
     */
    public static String getTimeDiffSeason(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime specificDateTime = LocalDateTime.parse(date, formatter);

        String timeDifference = getTimeDifference(specificDateTime);
        return timeDifference;
    }

    /**
     * Calculates the time difference between the given date string and the current time.
     * The date string must be in the format "yyyy-MM-dd HH:mm:ss".
     * If the date string does not contain a time component, it assumes the time as "00:00:00".
     * Returns a string representation of the time difference.
     *
     * @param date The date string to calculate the time difference from.
     * @return A string representation of the time difference.
     */
    public static String getDiffString(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime timestamp;
        if (date.contains("00:00:00")) {
            timestamp = LocalDateTime.parse(date, formatter);
        } else {
            timestamp = LocalDateTime.parse(date + " 00:00:00", formatter);
        }
        return TimestampConverter.getTimeDifference(timestamp);
    }
}
