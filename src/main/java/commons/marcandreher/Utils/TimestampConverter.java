package commons.marcandreher.Utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimestampConverter {

    private static Locale locale = Locale.ENGLISH; // Default language is English

    /**
     * Sets the language preference to German.
     */
    public static void setGermanLanguage() {
        locale = Locale.GERMAN;
    }

    /**
     * Sets the language preference to English (default).
     */
    public static void setEnglishLanguage() {
        locale = Locale.ENGLISH;
    }

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
            return getMessage("now");
        }

        if (period.getYears() > 0) {
            return getMessage(period.getYears() + (period.getYears() == 1 ? getMessage(" year") : getMessage(" years")));
        }

        if (period.getMonths() > 0) {
            return getMessage(period.getMonths() + (period.getMonths() == 1 ? getMessage(" month") : getMessage(" months")));
        }

        if (period.getDays() > 0) {
            return getMessage(period.getDays() + (period.getDays() == 1 ? getMessage(" day") : getMessage(" days")));
        }

        if (duration.toHours() > 0) {
            return getMessage(duration.toHours() + (duration.toHours() == 1 ? getMessage(" hour"): getMessage(" hours")));
        }

        if (duration.toMinutes() > 0) {
            return (duration.toMinutes() + (duration.toMinutes() == 1 ? getMessage(" minute") : getMessage(" minutes") ));
        }

        return getMessage("Invalid timestamp");
    }

    /**
     * Returns the localized message based on the provided key.
     *
     * @param key The key for the message.
     * @return The localized message.
     */
    private static String getMessage(String key) {
        if (locale == Locale.GERMAN) {
            switch (key) {
                case "now":
                    return "jetzt";
                case "Invalid timestamp":
                    return "Ungültiger Zeitstempel";
                case " minute":
                    return " Minute";
                case " minutes":
                    return " Minuten";
                case " hour":
                    return " Stunde";
                case " hours":
                    return " Stunden";
                case " day":
                    return " Tag";
                case " days":
                    return " Tage";
                case " month":
                    return " Monat";
                case " months":
                    return " Monate";
                case " year":
                    return " Jahr";
                case " years":
                    return " Jahre";
                default:
                    return key;
            }
        } else {
            return key;
        }
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
