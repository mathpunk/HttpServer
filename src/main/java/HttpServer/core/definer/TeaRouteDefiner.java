package HttpServer.core.definer;

import HttpServer.core.router.Router;

public class TeaRouteDefiner implements IRouteDefiner {
    private final Router router;

    public TeaRouteDefiner(Router router) {
        this.router = router;
        Handler teaHandler = new TeaHandler();
        this.router.defineRoute("/tea", "GET", teaHandler);
        this.router.defineRoute("/coffee", "GET", teaHandler);
    }

    @Override
    public Router getRouter() {
        return router;
    }

}
