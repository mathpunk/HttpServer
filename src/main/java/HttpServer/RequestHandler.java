package HttpServer;

import java.util.function.Function;

public class RequestHandler {

    private Function function;

    public RequestHandler(Function function) {
        this.function = function;
    }

    public Response apply(Request request) {
        return (Response) function.apply(request);
    }
}
