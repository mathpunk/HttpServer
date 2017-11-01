package HttpServer.controller;

import HttpServer.response.Response;
import HttpServer.router.Router;
import HttpServer.router.Routes;

public class FileController implements IController {

    private Router router;

    public FileController() {
        this.router = new Router(new Routes());
        serveFiles();
    }

    private void serveFiles() {
        router.defineRoute("/file1", "GET", new Response().setStatus(200));
        router.defineRoute("/text-file.txt", "GET", new Response().setStatus(200));
    }

    @Override
    public Router getRouter() {
        return router;
    }
}
