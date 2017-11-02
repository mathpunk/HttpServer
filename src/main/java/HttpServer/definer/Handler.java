package HttpServer.definer;

import HttpServer.response.Response;
import HttpServer.request.Request;

@FunctionalInterface
public interface Handler {
    Response apply(Request request);
}
