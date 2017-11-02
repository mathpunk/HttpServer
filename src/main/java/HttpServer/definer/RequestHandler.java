package HttpServer.definer;

import HttpServer.response.Response;
import HttpServer.request.Request;

import java.util.function.Function;

public class RequestHandler implements Handler {

    private Function function;

    public RequestHandler(Function function) {
        this.function = function;
    }

    public Response apply(Request request) {
        return (Response) function.apply(request);
    }
}
