package HttpServer.definer;

import HttpServer.router.Router;

public class TeaRouteDefiner implements IRouteDefiner {
    private final Router router;

    public TeaRouteDefiner(Router router) {
        this.router = router;
        addRoutes();
    }

    @Override
    public Router getRouter() {
        return router;
    }

    private void addRoutes() {
        Handler teaHandler = new TeaHandler();
        router.defineRoute("/tea", "GET", teaHandler);
        router.defineRoute("/coffee", "GET", teaHandler);
    }
}
