package HttpServer.core.definer;

import HttpServer.core.router.Router;

import java.util.HashMap;

public class RedirectDefiner implements IRouteDefiner {

    private final Router router;
    private final HashMap<String, String> redirectionMap;

    public RedirectDefiner(Router router, HashMap<String, String> redirectionMap) {
        this.router = router;
        this.redirectionMap = redirectionMap;
        Handler redirectHandler = new RedirectHandler(this.redirectionMap);
        for (String uri : this.redirectionMap.keySet() ) {
            this.router.defineRoute(uri, "GET", redirectHandler);
        }
    }

    @Override
    public Router getRouter() {
        return router;
    }
}
