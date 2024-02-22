package commons.marcandreher.Commons;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import commons.marcandreher.Utils.Color;

public class Flogger {

    private final String FOLDER_LOCATION = "logs/";

    private String instanceName;
    private int logLevel;
    public static Flogger instance;

    public enum Prefix {
        INFO(Color.CYAN + "[Info] " + Color.RESET), // INFO prefix
        ERROR(Color.RED + "[ERROR] " + Color.RESET), // ERROR prefix
        IMPORTANT(Color.RED + "[IMPORTANT] " + Color.RED), // IMPORTANT prefix
        WARNING(Color.YELLOW + "[WARNING] " + Color.RESET), // WARNING prefix
        MYSQL(Color.BLUE + "[MySQL] " + Color.RESET), // MYSQL prefix
        INPUT(Color.MAGENTA + "[Input] " + Color.RESET), // INPUT prefix
        OPENID(Color.GREEN + "[OpenID] " + Color.RESET),
        ACTION(Color.GREEN + "[Action] " + Color.RESET), // CRAWLER prefix
        API(Color.MAGENTA + "[API]: " + Color.RESET);

        private final String code;

        Prefix(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }
    }

    public void setInstanceName(String name) {
        this.instanceName = name;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setLogLevel(int logLevel) {
        this.logLevel = logLevel;
    }

    public int getLogLevel() {
        return logLevel;
    }

    public Flogger(int logLevel) {
        instance = this;
        this.logLevel = logLevel;
    }

    /**
     * Logs a message with the specified prefix and level.
     * 
     * @param prefix  the prefix to be added to the log message
     * @param message the log message
     * @param level   the level of the log message
     */
    public void log(Prefix prefix, String message, int level) {
        if (level <= logLevel)
            handleLog(prefix, message);
    }

    public void log(String message, int level) {
        if (level <= logLevel)
            handleLog(null, message);
    }

    private void handleLog(Prefix prefix, String message) {
        if (prefix != null) {
            System.out.println(prefix + message);
        } else {
            System.out.println(message);
        }

        if (instanceName != null) {
            String fileName = new SimpleDateFormat("yyyyMMdd'.txt'").format(new Date());
            Path folderPath = Paths.get(FOLDER_LOCATION);
            Path filePath = Paths.get(FOLDER_LOCATION, instanceName + fileName);

            try {
                if (!Files.exists(folderPath)) {
                    Files.createDirectories(folderPath);
                }

                if (!Files.exists(filePath)) {
                    Files.createFile(filePath);
                }
                String text = "";
                if(prefix != null) message += prefix.name();
                text += message.replaceAll("\\d{1,2}(;\\d{1,2})?", "").replaceAll("", "\n").replaceAll("[m", "");

                try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8,
                        StandardOpenOption.APPEND)) {
                    writer.write(text);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
