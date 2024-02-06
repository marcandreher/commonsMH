package commons.marcandreher.Commons;

import java.util.Set;

import commons.marcandreher.Engine.UploadHandler;
import commons.marcandreher.Utils.RequestType;
import spark.Route;
import spark.Spark;
import java.util.Objects;

public class Router {


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
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
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

    public Router() {
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

    


    
}
