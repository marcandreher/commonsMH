package commons.marcandreher.Engine;

import commons.marcandreher.Commons.MySQL;
import spark.Request;
import spark.Response;

public interface UploadAction {
    public boolean beforeUpload(Request req, Response res, MySQL mysql);
    public void afterUpload(Request req, Response res, MySQL mysql);
    public String chooseFileName(Request req, Response res, MySQL mysql, String submitted);
}
