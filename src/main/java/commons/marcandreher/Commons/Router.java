package commons.marcandreher.Commons;

import java.util.Objects;
import java.util.Set;

import commons.marcandreher.Auth.DiscordLogin;
import commons.marcandreher.Auth.DiscordLoginHandler;
import commons.marcandreher.Commons.Flogger.Prefix;
import commons.marcandreher.Engine.UploadHandler;
import commons.marcandreher.Utils.RequestType;
import dev.coly.discordoauth2.DiscordAPI;
import dev.coly.discordoauth2.objects.Tokens;
import dev.coly.discordoauth2.objects.User;
import spark.Response;
import spark.Route;
import spark.Spark;

public class Router {

    private Flogger logger;

    public Flogger getLogger() {
        return this.logger;
    }

    public void setLogger(Flogger logger) {
        this.logger = logger;
    }

    public Set<RoutePair> getRoutes() {
        return this.routes;
    }

    public void setRoutes(Set<RoutePair> routes) {
        this.routes = routes;
    }

    public class RoutePair {
        private RequestType requestType;
        private String route;

        public RoutePair(RequestType requestType, String route) {
            this.requestType = requestType;
            this.route = route;
        }

        public RequestType getRequestType() {
            return requestType;
        }

        public String getRoute() {
            return route;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            RoutePair routePair = (RoutePair) o;
            return requestType == routePair.requestType &&
                    Objects.equals(route, routePair.route);
        }

        @Override
        public int hashCode() {
            return Objects.hash(requestType, route);
        }
    }

    Set<RoutePair> routes;

    public Router(Flogger logger) {
        this.logger = logger;
        routes = new java.util.HashSet<>();

    }

    public void addUploadHandler(String location, UploadHandler handler) {
        routes.add((new RoutePair(RequestType.POST, location)));
        Spark.before(location, handler);
    }

    public void addGZipCompression() {
        Spark.after((req, res) -> {
            res.header("Content-Encoding", "gzip");
        });
    }

    public void get(String route, Route routeClass) {
        routes.add(new RoutePair(RequestType.GET, route));
        Spark.get(route, routeClass);
    }

    public void post(String route, Route routeClass) {
        routes.add(new RoutePair(RequestType.POST, route));
        Spark.post(route, routeClass);
    }

    public void put(String route, Route routeClass) {
        routes.add(new RoutePair(RequestType.PUT, route));
        Spark.put(route, routeClass);
    }

    public void delete(String route, Route routeClass) {
        routes.add(new RoutePair(RequestType.DELETE, route));
        Spark.delete(route, routeClass);
    }

    public void patch(String route, Route routeClass) {
        routes.add(new RoutePair(RequestType.PATCH, route));
        Spark.patch(route, routeClass);
    }

    public void registerDiscordRoute(String route, String redirect, DiscordLoginHandler handler, DiscordLogin dc) {
        Spark.get(route, new Route() {
            @Override
            public Object handle(spark.Request request, Response response) {
                User u = null;
                try {
                    Tokens tokens = dc.auth.getTokens(request.queryParams("code"));
                    u = DiscordAPI.getUser(tokens.getAccessToken());
                    logger.log(Prefix.INFO, "-> Discord Login", 3);
                } catch (Exception e) {
                    logger.log(Prefix.ERROR, "Discord login failed", 0);
                    return null;
                }

                handler.handleDiscordLogin(u, request, response);

                response.redirect(redirect);
                return null;

            }
        });
    }

}
