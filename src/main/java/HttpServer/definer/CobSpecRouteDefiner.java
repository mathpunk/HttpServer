package HttpServer.definer;

import HttpServer.router.Router;

import java.util.HashMap;

public class CobSpecRouteDefiner implements IRouteDefiner {

    private final Router router;

    public CobSpecRouteDefiner() {
        Router blankRouter = new Router();

        FileRouteDefiner fileDefiner = new FileRouteDefiner("./cob_spec/public", blankRouter);

        TeaRouteDefiner teaDefiner = new TeaRouteDefiner(fileDefiner.getRouter());

        HashMap<String, String> redirectionMap = new HashMap<String, String>();
        redirectionMap.put("/redirect", "/");
        RedirectDefiner redirectDefiner = new RedirectDefiner(teaDefiner.getRouter(), redirectionMap);

        this.router = redirectDefiner.getRouter();

        // Options tests are a concern of the router, in my opinion. Not set with a definer.
        Handler okHandler = new FunctionalHandler(200);
        router.defineRoute("/method_options", "GET", okHandler);
        router.defineRoute("/method_options", "HEAD", okHandler);
        router.defineRoute("/method_options", "POST", okHandler);
        router.defineRoute("/method_options", "OPTIONS", okHandler);
        router.defineRoute("/method_options", "PUT", okHandler);

        router.defineRoute("/method_options2", "GET", okHandler);
        router.defineRoute("/method_options2", "OPTIONS", okHandler);

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
