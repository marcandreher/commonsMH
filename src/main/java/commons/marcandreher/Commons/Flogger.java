package commons.marcandreher.Commons;

import commons.marcandreher.Utils.Color;

public class Flogger {
    public enum Prefix {
        INFO(Color.CYAN + "[INFO]: " + Color.RESET), // INFO prefix
        ERROR(Color.RED + "[ERROR]: " + Color.RESET), // ERROR prefix
        IMPORTANT(Color.RED + "[IMPORTANT]: " + Color.RED), // IMPORTANT prefix
        WARNING(Color.YELLOW + "[WARNING]: " + Color.RESET), // WARNING prefix
        MYSQL(Color.BLUE + "[MYSQL]: " + Color.RESET), // MYSQL prefix
        VOTE(Color.BLUE + "[VOTE] " + Color.RESET), // VOTE prefix
        INPUT(Color.MAGENTA + "[INPUT]: " + Color.RESET), // INPUT prefix
        OPENID(Color.GREEN + "[OPENID]: " + Color.RESET),
        CRAWLER(Color.GREEN + "[CRAWLER] " + Color.RESET); // CRAWLER prefix
    
        private final String code;
    
        Prefix(String code) {
            this.code = code;
        }
    
        @Override
        public String toString() {
            return code;
        }
    }

    private int logLevel;

    public void setLogLevel(int logLevel) {
        this.logLevel = logLevel;
    }
    
    public int getLogLevel() {
        return logLevel;
    }

    public Flogger(int logLevel) {
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
        if(level <= logLevel)
        System.out.println(prefix + message);
    }
    
}
