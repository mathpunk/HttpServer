package HttpServer.core.responder;

import HttpServer.core.message.response.Response;
import HttpServer.core.message.request.Request;

import java.util.function.Function;

public class FunctionalResponder implements Responder {

    private Function function;

    public FunctionalResponder(Function function) {
        this.function = function;
    }

    public FunctionalResponder(int statusCode) {
        Response response = new Response();
        response.setStatus(statusCode);
        this.function = (request) -> response;
    }

    public Response respond(Request request) {
        return (Response) function.apply(request);
    }
}
