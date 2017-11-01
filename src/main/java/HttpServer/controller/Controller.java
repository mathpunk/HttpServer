package HttpServer.controller;

import HttpServer.request.Request;
import HttpServer.response.Response;
import HttpServer.router.Router;
import HttpServer.router.Routes;

public class Controller {

    private final Router router;

    public Controller() {
        this.router = new Router(new Routes());
    }

    public void init() {
        serveRoot();
        serveForm();
        serveTea();
        serveFiles();
    }

    public Response route(Request request) {
        return router.route(request);
    }

    private void serveFiles() {
        router.defineRoute("/file1", "GET", new Response().setStatus(200));
        router.defineRoute("/text-file.txt", "GET", new Response().setStatus(200));
    }

    private void serveTea() {
        router.defineRoute("/tea", "GET", new Response().setStatus(200));
        RequestHandler coffeeHandler = new RequestHandler((request) -> {
            Response response = new Response().setStatus(418);
            response.setBody("I'm a teapot");
            return response;
        });
        router.defineRoute("/coffee", "GET", coffeeHandler);
    }

    private void serveForm() {
        router.defineRoute("/form", "PUT", new Response().setStatus(200));
    }

    private void serveRoot() {
        router.defineRoute("/", "GET", new Response().setStatus(200));
        router.defineRoute("/", "HEAD", new Response().setStatus(200));
    }

}
