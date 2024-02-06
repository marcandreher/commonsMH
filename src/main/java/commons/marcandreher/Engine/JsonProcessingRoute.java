package commons.marcandreher.Engine;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import commons.marcandreher.Commons.Database;
import commons.marcandreher.Commons.MySQL;
import spark.Request;
import spark.Response;
import spark.Route;

public class JsonProcessingRoute implements Route {

    public MySQL mysql = null;
    public ObjectMapper objectMapper = null;

    private List<String> requiredParamters;
    private ServerResponse sr;
    public Request request;
    public Response response;

    public JsonProcessingRoute(List<String> requiredParamters) {
        this.requiredParamters = requiredParamters;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        this.request = request;
        this.response = response;
        mysql = Database.getConnection();
        objectMapper = new ObjectMapper();

        response.type("application/json");
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        for (String parameter : requiredParamters) {
            if (request.queryParams(parameter) == null) {
                return missingParameters(parameter);
            }
        }

        return this;
    }

    public String returnResponse(Object toMap) {
        response.status(200);
       
        closeDb();
        try {
            return objectMapper.writeValueAsString(toMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String missingParameters(String parameter) {
        response.status(500);
        sr = new ServerResponse();
        sr.setCode(500);
        sr.setMessage("missing parameter: " + parameter);
        closeDb();
        try {
            return objectMapper.writeValueAsString(sr);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String notFound(String parameter) {
        response.status(404);
        sr = new ServerResponse();
        sr.setCode(404);
        sr.setMessage("not found: " + parameter);
        closeDb();
        try {
            return objectMapper.writeValueAsString(sr);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String expectedType(String parameter, String expectedType) {
        response.status(500);
        sr = new ServerResponse();
        sr.setCode(500);
        sr.setMessage("parameter: " + parameter+":expectedType: "+expectedType);
        closeDb();
        try {
            return objectMapper.writeValueAsString(sr);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public String internalError() {
        response.status(500);
        sr = new ServerResponse();
        sr.setCode(500);
        sr.setMessage("internal error");
        closeDb();
        try {
            return objectMapper.writeValueAsString(sr);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void closeDb() {
        mysql.close();
    }

}
