package commons.marcandreher.Engine;

import spark.Request;
import spark.Response;

public class HealthRoute extends JsonProcessingRoute {

    @Override
    public Object handle(Request request, Response response) {
        super.handle(request, response);
        ServerResponse serverResponse = new ServerResponse();
        serverResponse.setCode(200);
        serverResponse.setMessage("OK");
        return returnResponse(serverResponse);
    }
    
}
