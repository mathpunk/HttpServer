package HttpServer.controller;

import HttpServer.Resource;
import HttpServer.response.Response;
import HttpServer.router.Router;
import HttpServer.router.Routes;

public class FileController implements IController {

    private Router router;

    public FileController() {
        this.router = new Router(new Routes());
        init();
    }

    private void init() {
        Resource firstFile = new Resource(new Object());
        firstFile.setUri("/file1");
        Resource secondFile = new Resource(new Object());
        secondFile.setUri("/text-file.txt");

        RequestHandler okHandler = new RequestHandler((whatever) -> new Response().setStatus(200));
        firstFile.setHandler("GET", okHandler);
        secondFile.setHandler("GET", okHandler);

        Resource[] iterator = {firstFile, secondFile};
        for (Resource resource : iterator) {
            router.defineRoute(resource.getUri(), "GET", okHandler);
        }
    }

    

    @Override
    public Router getRouter() {
        return router;
    }
}
