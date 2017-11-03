package HttpServer.definer;

import HttpServer.response.Response;
import HttpServer.router.Router;

public class CobSpecRouteDefiner implements IRouteDefiner {

    private final Router router;

    public CobSpecRouteDefiner() {
        FileRouteDefiner fileController = new FileRouteDefiner("./cob_spec/public");
        this.router = fileController.getRouter();
        serveRoot();
        serveForm();
        serveTea();
    }

    @Override
    public Router getRouter() {
        return router;
    }

    private void serveTea() {
        router.defineRoute("/tea", "GET", new FunctionalHandler(200));
        Handler coffeeHandler = new FunctionalHandler((request) -> {
            Response response = new Response();
            response.setStatus(418);
            response.setBody("I'm a teapot");
            return response;
        });
        router.defineRoute("/coffee", "GET", coffeeHandler);
    }

    private void serveForm() {
        router.defineRoute("/form", "PUT", new FunctionalHandler(200));
    }

    private void serveRoot() {
        router.defineRoute("/", "GET", new FunctionalHandler(200));
        router.defineRoute("/", "HEAD", new FunctionalHandler(200));
    }
}
