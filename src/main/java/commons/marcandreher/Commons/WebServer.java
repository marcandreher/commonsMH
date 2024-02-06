package commons.marcandreher.Commons;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import commons.marcandreher.Commons.Flogger.Prefix;
import commons.marcandreher.Input.CommandHandler;
import commons.marcandreher.Input.Commands.Webserver;
import commons.marcandreher.Utils.Color;
import freemarker.cache.CacheStorage;
import freemarker.cache.NullCacheStorage;
import freemarker.cache.SoftCacheStorage;
import freemarker.cache.StrongCacheStorage;
import freemarker.template.Configuration;
import spark.Spark;

/**
 * The WebServer class represents a web server that can be ignited to serve web
 * content.
 */
public class WebServer {

    public static Configuration freemarkerCfg = new Configuration(Configuration.VERSION_2_3_23);
    private int webPort = 80;
    private short cacheLevel;
    private String webIp = "127.0.0.1";
    private Flogger logger;

    private String staticFiles;
    private String templateFiles;


    public int getWebPort() {
        return this.webPort;
    }

    public void setWebPort(int webPort) {
        Spark.port(webPort);
        this.webPort = webPort;
    }

    public String getWebIp() {
        return this.webIp;
    }

    public void setWebIp(String webIp) {
        Spark.ipAddress(webIp);
        this.webIp = webIp;
    }

    /**
     * Constructs a WebServer object with the specified logger.
     *
     * @param logger the logger to be used for logging messages
     */
    public WebServer(Flogger logger, Database database, short cacheLevel) {
        this.cacheLevel = cacheLevel;
        this.logger = logger;

        Logger.getLogger("org").setLevel(Level.OFF);
        Logger.getLogger("akka").setLevel(Level.OFF);
    }

    public void addDefaultDirectories(String templateDir, String staticDir) {
        Spark.staticFiles.location(staticDir);
        staticFiles = staticDir;
        templateFiles = templateDir;
    }

    /**
     * Ignites the web server using the default IP address and port.
     * 
     * @throws IOException
     */
    public void ignite() throws IOException {
        Spark.port(webPort);
        Spark.ipAddress(webIp);
        runWebServer();
    }

    /**
     * Ignites the web server using the specified IP address and port.
     *
     * @param webIp   the IP address to bind the web server to
     * @param webPort the port to listen on
     * @throws IOException
     */
    public void ignite(String webIp, int webPort) throws IOException {
        Spark.port(webPort);
        Spark.ipAddress(webIp);
        this.webPort = webPort;
        this.webIp = webIp;
        runWebServer();
    }

    public void ignite(String webIp, int webPort, CommandHandler handler) throws IOException {
        Spark.port(webPort);
        Spark.ipAddress(webIp);

        this.webPort = webPort;
        this.webIp = webIp;

        Webserver webServer = new Webserver();
        webServer.setWebServer(this);
        handler.registerCommand(webServer);
        
        runWebServer();
    }

    private void runWebServer() throws IOException {
        logger.log(Prefix.INFO, "MHCommons Webserver ignited on " + Color.GREEN + webIp + ":" + webPort, 1);

        if (staticFiles == null || templateFiles == null) {
            logger.log(Prefix.WARNING, "Static and template directories not set", 1);
            return;
        } else {
            File staticFolder = new File(staticFiles);
            if (!staticFolder.exists()) {
                staticFolder.createNewFile();
            }
            Spark.externalStaticFileLocation(staticFiles);

            File templateFolder = new File(templateFiles);
            if (!templateFolder.exists()) {
                templateFolder.createNewFile();
            }
            freemarkerCfg.setDirectoryForTemplateLoading(templateFolder);
        }

        switch (cacheLevel) {
            case 1:
                freemarkerCfg.setCacheStorage(NullCacheStorage.INSTANCE);
                freemarkerCfg.setTemplateUpdateDelayMilliseconds(1);
                break;
            case 2:
                CacheStorage level2CacheStorage = new SoftCacheStorage();
                freemarkerCfg.setCacheStorage(level2CacheStorage);
                freemarkerCfg.setTemplateUpdateDelayMilliseconds(500);
                break;
            case 3:
                CacheStorage level3CacheStorage = new StrongCacheStorage();

                freemarkerCfg.setCacheStorage(level3CacheStorage);
                freemarkerCfg.setTemplateUpdateDelayMilliseconds(1000);
                break;
        }

        Spark.before((req, res) -> {
            logger.log(Prefix.INFO, "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    + "] " + req.ip() + " | " + req.url() + " | " + req.userAgent(), 2);
        });

        Spark.awaitInitialization();
    }

    public void addGZipCompression() {
        Spark.after((req, res) -> {
            res.header("Content-Encoding", "gzip");
        });
    }

    /**
     * Sets the thread pool configuration for the web server.
     *
     * @param minThreads    the minimum number of threads in the thread pool
     * @param maxThreads    the maximum number of threads in the thread pool
     * @param timeOutMillis the timeout in milliseconds for idle threads in the
     *                      thread pool
     */
    public void setThreadPool(int minThreads, int maxThreads, int timeOutMillis) {
        Spark.threadPool(maxThreads, minThreads, timeOutMillis);
    }

}
