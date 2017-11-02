package HttpServer.definer;

import HttpServer.response.Response;
import HttpServer.request.Request;

import java.util.function.Function;

public class FunctionalHandler implements Handler {

    private Function function;

    public FunctionalHandler(Function function) {
        this.function = function;
    }

    public FunctionalHandler(int statusCode) {
        Response response = new Response();
        response.setStatus(statusCode);
        this.function = (request) -> response;
    }

    public Response respond(Request request) {
        return (Response) function.apply(request);
    }
}
