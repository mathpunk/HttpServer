package HttpServer.router;

import HttpServer.request.Request;
import HttpServer.controller.RequestHandler;
import HttpServer.response.Response;

public class Router {

    private final Routes routes;

    public Router(Routes routes) {
        this.routes = routes;
        routeCobSpec();
    }

    public void defineRoute(String uri, String method, RequestHandler handler) {
        routes.define(uri, method, handler);
    }

    public void defineRoute(String uri, String method, Response response) {
        RequestHandler handler = new RequestHandler((request) -> response);
        defineRoute(uri, method, handler);
    }

    public Response respond(Request request) {
        String verb = request.getMethod();
        String uri = request.getUri();
        return route(uri, verb);
    }

    public Response route(String uri, String method) {
        RequestHandler handler = routes.retrieve(uri, method);
        return handler.apply(new Request());
    }

    public void serveRoot() {
        RequestHandler ok = new RequestHandler(request -> new Response().setStatus(200));
        defineRoute("GET", "/", ok);
        defineRoute("HEAD", "/", ok);
    }

    public void serveForm() {
        RequestHandler ok = new RequestHandler(request -> new Response().setStatus(200));
        defineRoute("GET", "/form", ok);
        defineRoute("PUT", "/form", ok);
    }

    public void serveFiles() {
        RequestHandler ok = new RequestHandler(request -> new Response().setStatus(200));
        defineRoute("GET", "/file1", ok);
        defineRoute("GET", "/text-file.txt", ok);

//        put	/file1
//        ensure	response code equals	405

//        bogus Request	/file1
//        ensure	response code equals	405

//        post	/text-file.txt
//        ensure	response code equals	405

//        bogus Request	/file1
//        ensure	response code equals	405

    }

    public void routeCobSpec() {
        serveTea();
        serveFiles();
        serveRoot();
        serveForm();
    }

    public void serveTea() {
        RequestHandler teaResponse = new RequestHandler(request -> new Response().setStatus(200));
        RequestHandler coffeeResponse = new RequestHandler(request -> {
            Response response = new Response();
            response.setStatus(418);
            response.setBody("I'm a teapot");
            return response;
        });
        defineRoute("GET", "/tea", teaResponse);
        defineRoute("GET", "/coffee", coffeeResponse);
    }

    public Response route(Request request) {
        return respond(request);
    }
}
