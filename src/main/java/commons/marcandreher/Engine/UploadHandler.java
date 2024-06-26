package commons.marcandreher.Engine;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import commons.marcandreher.Commons.Database;
import commons.marcandreher.Commons.Flogger;
import commons.marcandreher.Commons.Flogger.Prefix;
import commons.marcandreher.Commons.MySQL;
import spark.Filter;
import spark.Request;
import spark.Response;

public class UploadHandler implements Filter {

    private MultipartConfigElement multipartConfig;
    private UploadAction action = null;
    
    private String location;
    private String fileBoxName = "file";

    public UploadHandler(String location, long maxFileSize, long maxRequestSize, int fileSizeThreshold) {
        this.location = location;
        this.multipartConfig = new MultipartConfigElement(location, maxFileSize, maxRequestSize, fileSizeThreshold);
    }

    public void setMultipartConfig(Request request) {
        request.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfig);
    }

    public Part uploadFile(Request request, String outputPath, String outputName, String fileInput) {
        try {
            Part filePart = request.raw().getPart(fileInput);
            InputStream inputStream = filePart.getInputStream();
           
            OutputStream outputStream = new FileOutputStream(outputPath + outputName);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            inputStream.close();
            return filePart;
        } catch (Exception e) {
           Flogger.instance.error(e);
        }
        return null;
    }

    public void setFileBoxName(String fileBoxName) {
        this.fileBoxName = fileBoxName;
    }

    public String getFileBoxName() {
        return this.fileBoxName;
    }

    public void setAction(UploadAction action) {
        this.action = action;
    }  

    public UploadAction getAction() {
        return this.action;
    }
    

    @Override
    public void handle(Request request, Response response) throws Exception {
        setMultipartConfig(request);

        MySQL mysql = null;
        try {
            if (action != null) {
                // Get MySQL connection
                mysql = Database.getConnection();
                // Execute actions before upload
                if(!action.beforeUpload(request, response, mysql)) {
                    return;
                }
            }

            // Get the uploaded file part named "img"
            Part filePart = request.raw().getPart(fileBoxName);
            if (filePart != null) {
                // Get the InputStream to read the contents of the file
                try (InputStream fileContent = filePart.getInputStream()) {
                    // Define the directory where you want to save the file
                    Path targetPath = Paths.get(location, action.chooseFileName(request, response, mysql, filePart.getSubmittedFileName()));
                    // Save the file to the specified directory
                    Files.copy(fileContent, targetPath, StandardCopyOption.REPLACE_EXISTING);
                }
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