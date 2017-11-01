package HttpServer.controller;

import HttpServer.response.Response;
import HttpServer.request.Request;

@FunctionalInterface
public interface Handler {
    Response apply(Request request);
}
