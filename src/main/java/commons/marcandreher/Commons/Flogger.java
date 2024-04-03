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
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import commons.marcandreher.Utils.Color;

public class Flogger {

    private final String FOLDER_LOCATION = "logs/";

    private String instanceName;
    private int logLevel;
    public static Flogger instance;

    private Queue<String> logQueue = new ConcurrentLinkedQueue<>();
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    
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

    public void error(Exception e) {
        log(Prefix.ERROR, e.getMessage() + " at:", 0);
        for (StackTraceElement element : e.getStackTrace()) {
            log(Prefix.ERROR, element.toString(), 0);
        }
    }

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
           
            if (!executor.isShutdown()) {
                executor.execute(() -> {
                    String fileName = new SimpleDateFormat("yyyyMMdd'.log'").format(new Date());
                    Path filePath = Paths.get(FOLDER_LOCATION, instanceName + fileName);
        
                    String text = "";
                    if (prefix != null) text += prefix.name() + " ";
                    text += message.replaceAll("\\d{1,2}(;\\d{1,2})?", "").replace("[m", "");
                    if (text.contains("")) {
                        text = text.replace("", "\n");
                    } else {
                        text += "\n";
                    }
        
                    logQueue.offer(text); // Enqueue log message
        
                    try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8,
                            StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                        String log;
                        while ((log = logQueue.poll()) != null) { // Dequeue log messages
                            writer.write(log);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    public void shutdown() {
        executor.shutdown();
    }
}
