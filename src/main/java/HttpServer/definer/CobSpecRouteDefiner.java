package HttpServer.definer;

import HttpServer.response.Response;
import HttpServer.router.Router;

public class CobSpecRouteDefiner implements IRouteDefiner {

    private final Router router;

    public CobSpecRouteDefiner() {
        Router blankRouter = new Router();
        FileRouteDefiner fileDefiner = new FileRouteDefiner("./cob_spec/public", blankRouter);
        TeaRouteDefiner teaDefiner = new TeaRouteDefiner(fileDefiner.getRouter());
        this.router = teaDefiner.getRouter();
        serveRoot();
        serveForm();
    }

    @Override
    public Router getRouter() {
        return router;
    }

    private void serveForm() {
        router.defineRoute("/form", "PUT", new FunctionalHandler(200));
    }

    private void serveRoot() {
        router.defineRoute("/", "GET", new FunctionalHandler(200));
        router.defineRoute("/", "HEAD", new FunctionalHandler(200));
    }
}
