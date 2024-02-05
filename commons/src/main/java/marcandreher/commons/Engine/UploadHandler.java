package marcandreher.commons.Engine;

import javax.servlet.MultipartConfigElement;

import spark.Filter;
import spark.Request;
import spark.Response;

public class UploadHandler implements Filter {

    private MultipartConfigElement multipartConfig;

    // Add before check
    public UploadHandler(String location, long maxFileSize, long maxRequestSize, int fileSizeThreshold) {
        this.multipartConfig = new MultipartConfigElement(location, maxFileSize, maxRequestSize, fileSizeThreshold);
    }

    @Override
    public void handle(Request request, Response response) throws Exception {
        request.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfig);
    }

}
