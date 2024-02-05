package commons.marcandreher.Commons;

import commons.marcandreher.Engine.UploadHandler;
import spark.Spark;

public class Router {

    public static void addUploadHandler(String location, UploadHandler handler) {
        Spark.before(location, handler);
    }
    
}
