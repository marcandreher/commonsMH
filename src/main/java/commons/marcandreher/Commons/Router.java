package commons.marcandreher.Commons;

import java.util.Objects;
import java.util.Set;

import commons.marcandreher.Auth.DiscordLogin;
import commons.marcandreher.Auth.DiscordLoginHandler;
import commons.marcandreher.Commons.Flogger.Prefix;
import commons.marcandreher.Engine.FullstackRoute;
import commons.marcandreher.Engine.HealthRoute;
import commons.marcandreher.Engine.UploadHandler;
import commons.marcandreher.Utils.RequestType;
import dev.coly.discordoauth2.DiscordAPI;
import dev.coly.discordoauth2.DiscordOAuth2;
import dev.coly.discordoauth2.objects.User;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * The Router class handles routing and request handling for the application.
 */
public class Router {

    private Flogger logger;
    private Set<RoutePair> routes;

    public Router(Flogger logger) {
        this.logger = logger;
        routes = new java.util.HashSet<>();
    }

    /**
     * Gets the logger instance.
     *
     * @return The logger instance.
     */
    public Flogger getLogger() {
        return this.logger;
    }

    /**
     * Sets the logger instance.
     *
     * @param logger The logger instance to set.
     */
    public void setLogger(Flogger logger) {
        this.logger = logger;
    }

    /**
     * Gets the set of route pairs.
     *
     * @return The set of route pairs.
     */
    public Set<RoutePair> getRoutes() {
        return this.routes;
    }

    /**
     * Sets the set of route pairs.
     *
     * @param routes The set of route pairs to set.
     */
    public void setRoutes(Set<RoutePair> routes) {
        this.routes = routes;
    }

    /**
     * Represents a pair of request type and route.
     */
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

    /**
     * Adds an upload handler for the specified location.
     *
     * @param location The location to add the upload handler.
     * @param handler  The upload handler to add.
     */
    public void addUploadHandler(String location, UploadHandler handler) {
        routes.add((new RoutePair(RequestType.POST, location)));
        Spark.before(location, handler);
    }

    /**
     * Adds GZip compression to the response.
     */
    public void addGZipCompression() {
        Spark.after((req, res) -> {
            res.header("Content-Encoding", "gzip");
        });
    }

    public void filter(String path, Filter filter) {
        Spark.before(path, filter);
    }

    /**
     * Adds a GET route.
     *
     * @param route      The route to add.
     * @param routeClass The route class to handle the request.
     */
    public void get(String route, Route routeClass) {
        routes.add(new RoutePair(RequestType.GET, route));
        Spark.get(route, routeClass);
    }

    /**
     * Adds a POST route.
     *
     * @param route      The route to add.
     * @param routeClass The route class to handle the request.
     */
    public void post(String route, Route routeClass) {
        routes.add(new RoutePair(RequestType.POST, route));
        Spark.post(route, routeClass);
    }

    /**
     * Adds a PUT route.
     *
     * @param route      The route to add.
     * @param routeClass The route class to handle the request.
     */
    public void put(String route, Route routeClass) {
        routes.add(new RoutePair(RequestType.PUT, route));
        Spark.put(route, routeClass);
    }

    /**
     * Adds a DELETE route.
     *
     * @param route      The route to add.
     * @param routeClass The route class to handle the request.
     */
    public void delete(String route, Route routeClass) {
        routes.add(new RoutePair(RequestType.DELETE, route));
        Spark.delete(route, routeClass);
    }

    /**
     * Adds a PATCH route.
     *
     * @param route      The route to add.
     * @param routeClass The route class to handle the request.
     */
    public void patch(String route, Route routeClass) {
        routes.add(new RoutePair(RequestType.PATCH, route));
        Spark.patch(route, routeClass);
    }

    public void registerHealthRoute(String route) {
        get(route, new HealthRoute());
    }

    /**
     * Registers a Discord route.
     *
     * @param route    The route to register.
     * @param redirect The redirect URL.
     * @param handler  Your Discord login handler.
     * @param dc       The Discord login instance.
     */
    public void registerDiscordRoute(String route, DiscordLoginHandler handler, DiscordLogin dc) {
        DiscordOAuth2 dcauth2 = dc.auth;


        Spark.get(route, new FullstackRoute() {
            @Override
            public Object handle(Request request, Response response) {
                super.handle(request, response);
                logger.log(Prefix.INFO, "Discord login requested", 2);
                
                try {
                    logger.log(Prefix.INFO, "-> Discord Login", 2);
                    User u = DiscordAPI.getUser(dcauth2.getTokens(request.queryParams("code")).getAccessToken());
                    logger.log(Prefix.INFO, "Discord Handler called", 2);
                    return handler.handleDiscordLogin(u, request, response, mysql);
                    
                } catch (Exception e) {
                    logger.log(Prefix.ERROR, "Discord login failed " + e.getMessage(), 0);
                    return null;
                }
            }
        });
    }
}
