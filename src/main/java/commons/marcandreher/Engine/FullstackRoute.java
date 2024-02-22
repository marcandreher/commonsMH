package commons.marcandreher.Engine;


import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import commons.marcandreher.Auth.DiscordLogin;
import commons.marcandreher.Commons.Database;
import commons.marcandreher.Commons.Flogger;
import commons.marcandreher.Commons.MySQL;
import commons.marcandreher.Commons.WebServer;
import commons.marcandreher.Utils.Color;
import commons.marcandreher.Utils.Stopwatch;
import freemarker.template.Template;
import spark.Request;
import spark.Response;
import spark.Route;

public class FullstackRoute implements Route {

    private static final int LOG_STOPWATCH_THRESHOLD = 4;

    public Map<String, Object> webMap;
    private Stopwatch stopWatch;
    public MySQL mysql;
    public Flogger log;

    private Meta meta = null;

    private Request request;
    private Response response;

    public FullstackRoute() {
        this.webMap = new ConcurrentHashMap<>();
    }

    @Override
    public Object handle(Request request, Response response) {
        log = Flogger.instance;
        this.request = request;
        this.response = response;
        this.stopWatch = new Stopwatch();
        this.stopWatch.start();

        try {
            this.mysql = Database.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
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

    public void setMeta(Meta meta) {
        this.meta = meta;
    } 

    public Meta getMeta() {
        return meta;
    }

    public Object redirect(String location) {
        response.redirect(location);
        mysql.close();
        return null;
    }

    public Object notFound() {
        mysql.close();
        return null;
    }

    public String renderTemplate(String location) {
        try {
            Template templateFree = WebServer.freemarkerCfg.getTemplate(location);
            if(this.meta != null) {
                this.webMap.put("meta", this.meta);
            }
            try (Writer out = new StringWriter()) {
                this.mysql.close();
                templateFree.process(this.webMap, out);
                return out.toString();
            }

        } catch (IOException e) {
            log.log(Flogger.Prefix.ERROR, "Error loading template: " + Color.GREEN + location + Color.RESET, 0);
            throw new RuntimeException("Error loading template: " + location, e);
           
        } catch (Exception e) {
            log.log(Flogger.Prefix.ERROR, "Error loading template: " + Color.GREEN + location + Color.RESET, 0);
            response.status(500);
        } finally {
            this.mysql.close();
        }

        // Redirect to error page
        return null;
    }
}