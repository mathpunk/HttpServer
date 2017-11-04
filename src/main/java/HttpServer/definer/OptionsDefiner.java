package HttpServer.definer;

import HttpServer.router.Router;

public class OptionsDefiner implements IRouteDefiner {

    private final Router router;

    public OptionsDefiner(Router router) {
        this.router = router;
        addRoutes();
    }

    public void addRoutes() {
        Handler optionsHandler = new OptionsHandler(router);
        // This is very specific to cob_spec
        router.defineRoute("/method_options", "GET", optionsHandler);
        router.defineRoute("/method_options", "HEAD", optionsHandler);
        router.defineRoute("/method_options", "POST", optionsHandler);
        router.defineRoute("/method_options", "PUT", optionsHandler);
        router.defineRoute("/method_options", "OPTIONS", optionsHandler);
    }

    @Override
    public Router getRouter() {
        return router;
    }
//    options	/method_options
//    ensure	response code equals	200
//    ensure	response header allow contains	GET,HEAD,POST,OPTIONS,PUT
//    get	/method_options
//    put	/method_options
//    post	/method_options
//    head	/method_options
//    options	/method_options2
//    ensure	response code equals	200
//    ensure	response header allow contains	GET,OPTIONS
//    reject	response header allow contains	HEAD,POST,PUT
//    get	/method_options2
//    options	/method_options2
}
