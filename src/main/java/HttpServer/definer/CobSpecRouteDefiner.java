package HttpServer.definer;

import HttpServer.router.Router;

import java.util.HashMap;

public class CobSpecRouteDefiner implements IRouteDefiner {

    private final Router router;

    public CobSpecRouteDefiner() {
        Router blankRouter = new Router();

        OptionsDefiner optionsDefiner = new OptionsDefiner(blankRouter);

        FileRouteDefiner fileDefiner = new FileRouteDefiner("./cob_spec/public", optionsDefiner.getRouter());

        TeaRouteDefiner teaDefiner = new TeaRouteDefiner(fileDefiner.getRouter());

        HashMap<String, String> redirectionMap = new HashMap<String, String>();
        redirectionMap.put("/redirect", "/");
        RedirectDefiner redirectDefiner = new RedirectDefiner(teaDefiner.getRouter(), redirectionMap);
        this.router = redirectDefiner.getRouter();
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
