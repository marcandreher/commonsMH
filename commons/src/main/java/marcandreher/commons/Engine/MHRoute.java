package marcandreher.commons.Engine;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import freemarker.template.Template;
import marcandreher.commons.Auth.DiscordLogin;
import marcandreher.commons.Commons.Database;
import marcandreher.commons.Commons.Flogger;
import marcandreher.commons.Commons.MySQL;
import marcandreher.commons.Commons.WebServer;
import marcandreher.commons.Utils.Stopwatch;
import spark.Request;
import spark.Response;
import spark.Route;

public class MHRoute implements Route {

    private static final int LOG_STOPWATCH_THRESHOLD = 4;

    private Map<String, Object> webMap;
    private Stopwatch stopWatch;
    private MySQL mysql;
    private Flogger log;

    private Request request;
    private Response response;

    public MHRoute(Flogger logger) {
        log = logger;
        this.webMap = new ConcurrentHashMap<>();
    }

    @Override
    public Object handle(Request request, Response response) {
        this.stopWatch = new Stopwatch();
        this.stopWatch.start();

        try {
            this.mysql = Database.getConnection();
        } catch (SQLException e) {
            this.mysql.close();
            return handle(request, response);
        }
        
        if (this.log.getLogLevel() >= LOG_STOPWATCH_THRESHOLD) {
            this.stopWatch = new Stopwatch();
            this.stopWatch.start();
        }

        mapAssignment();
        return this;
    }

    private void mapAssignment() {
        this.webMap.clear();

        if (DiscordLogin.disUrl != null) {
            this.webMap.put("disUrl", DiscordLogin.disUrl);
        }
    }

    public Map<String, Object> retryMapAssignment() {
        if (this.webMap.isEmpty()) {
            handle(request, response);
        }
        return this.webMap;
    }

    public void setTitle(String title) {
        this.webMap.put("titleBar", title);
    }

    public String renderTemplate(String location) {
        try {
            Template templateFree = WebServer.freemarkerCfg.getTemplate(location);

            try (Writer out = new StringWriter()) {
                this.mysql.close();
                templateFree.process(this.webMap, out);
                return out.toString();
            }

        } catch (IOException e) {
            throw new RuntimeException("Error loading template: " + location, e);
        } catch (Exception e) {
            System.out.println("Error rendering template");
            response.status(500);
        } finally {
            this.mysql.close();
        }
        // Redirect to error page
        return null;
    }
}