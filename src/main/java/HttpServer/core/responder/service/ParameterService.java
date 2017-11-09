package HttpServer.core.responder;

import HttpServer.core.request.Request;
import HttpServer.core.response.Response;

import java.util.HashMap;

public class ParameterService implements Service {

    @Override
    public Response respond(Request request) {
        HashMap<String, String> parameters = request.getParameters();
        String key1 = "variable_1";
        String value1 = parameters.get("variable_1");
        String key2 = "variable_2";
        String value2 = parameters.get("variable_2");
        String body1 = key1 + " = " + value1 + "\n";
        String body2 = key2 + " = " + value2 + "\n";
        Response response = new Response();
        response.setStatus(200);
        response.setBody(body1+body2);
        return response;
    }
}
