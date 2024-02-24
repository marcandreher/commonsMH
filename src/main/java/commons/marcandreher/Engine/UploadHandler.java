package commons.marcandreher.Engine;

import javax.servlet.MultipartConfigElement;
 
import commons.marcandreher.Commons.Database;
import commons.marcandreher.Commons.MySQL;
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
        MySQL mysql = null;
        if (action != null) {
            mysql = Database.getConnection();
            if (!action.beforeUpload(request, response, mysql)) {
                return;
            }
        }

        request.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfig);

        if (action != null) {
            if (!action.afterUpload(request, response, mysql)) {
                return;
            }
            mysql.close();
        }
       
    }

}
