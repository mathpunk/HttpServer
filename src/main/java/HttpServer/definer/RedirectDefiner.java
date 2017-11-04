package HttpServer.definer;

import HttpServer.router.Router;

import java.util.HashMap;

public class RedirectDefiner implements IRouteDefiner {

    private final Router router;
    private final HashMap<String, String> redirectionMap;

    public RedirectDefiner(Router router, HashMap<String, String> redirectionMap) {
        this.router = router;
        this.redirectionMap = redirectionMap;
        addRoutes();
    }

    public void addRoutes() {
        Handler redirectHandler = new RedirectHandler(redirectionMap);
        for (String uri : redirectionMap.keySet() ) {
            router.defineRoute(uri, "GET", redirectHandler);
        }
    }

    @Override
    public Router getRouter() {
        return router;
    }
}
