package HttpServer.controller;

import HttpServer.response.Response;
import HttpServer.request.Request;

@FunctionalInterface
public interface Handler {
    public Response apply(Request request);
}
