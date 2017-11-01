package HttpServer.controller;

import HttpServer.router.Router;
import HttpServer.router.Routes;

public class Controller implements IController {

    private Router router;

    public Controller() {
        this.router = new Router(new Routes());
    }

    public Controller(Router router) {
        this.router = router;
    }

    @Override
    public Router getRouter() {
        return router;
    }

}