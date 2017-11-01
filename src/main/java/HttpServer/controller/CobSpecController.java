package HttpServer.controller;

import HttpServer.response.Response;
import HttpServer.router.Router;
import HttpServer.router.Routes;

public class CobSpecController implements IController {

    private final Router router;

    public CobSpecController() {
        this.router = new Router(new Routes());
        serveRoot();
        serveForm();
        serveTea();
        serveFiles();
    }

    @Override
    public Router getRouter() {
        return router;
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
