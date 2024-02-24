package commons.marcandreher.Engine;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletResponse;

import commons.marcandreher.Commons.Database;
import commons.marcandreher.Commons.Flogger;
import commons.marcandreher.Commons.MySQL;
import commons.marcandreher.Commons.Flogger.Prefix;
import spark.Filter;
import spark.Request;
import spark.Response;

public class UploadHandler implements Filter {

    private MultipartConfigElement multipartConfig;
    private UploadAction action = null;
    
    public UploadHandler(String location, long maxFileSize, long maxRequestSize, int fileSizeThreshold) {
        this.multipartConfig = new MultipartConfigElement(location, maxFileSize, maxRequestSize, fileSizeThreshold);
    }

    public void setAction(UploadAction action) {
        this.action = action;
    }  

    public UploadAction getAction() {
        return this.action;
    }
    

    @Override
    public void handle(Request request, Response response) throws Exception {
        // Set the multipart configuration for the request
        request.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfig);

        MySQL mysql = null;
        try {
            if (action != null) {
                // Get MySQL connection
                mysql = Database.getConnection();
                // Execute actions before upload
                action.beforeUpload(request, response, mysql);
            }

            // Action after upload
            if (action != null) {
                action.afterUpload(request, response, mysql);
            }
        } catch (Exception e) {
        
            Flogger.instance.log(Prefix.ERROR, e.getMessage(), 0);
            e.printStackTrace();

            response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.body("Internal Server Error");
        } finally {
            if (mysql != null) {
                mysql.close();
            }
        }
    }
}