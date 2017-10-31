package HttpServer;

import HttpServer.request.Request;
import HttpServer.controller.RequestHandler;
import HttpServer.request.RequestParser;
import HttpServer.response.Response;
import HttpServer.response.ResponseWriter;
import HttpServer.router.Router;
import HttpServer.router.Routes;
import HttpServer.socket.ReadableSocket;
import HttpServer.socket.WritableSocket;
import HttpServer.utility.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SynchronousListener {

    private final Logger logger;
    private final Router router;
    private final int port;
    private final String directory;

    public SynchronousListener(int port, String directory, Logger logger) {
        this.port = port;
        this.directory = directory;
        this.logger = logger;
        Routes routes = new Routes();
        this.router = new Router(routes);

        // Cob spec specific routing
        router.defineRoute("/", "GET", new Response().setStatus(200));
        router.defineRoute("/form", "PUT", new Response().setStatus(200));
        router.defineRoute("/tea", "GET", new Response().setStatus(200));
        RequestHandler coffeeHandler = new RequestHandler((request) -> {
            Response response = new Response().setStatus(418);
            response.setBody("I'm a teapot");
            return response;
        });
        router.defineRoute("/coffee", "GET", coffeeHandler);
        router.defineRoute("/", "HEAD", new Response().setStatus(200));
        router.defineRoute("/file1", "GET", new Response().setStatus(200));
        router.defineRoute("/text-file.txt", "GET", new Response().setStatus(200));

    }


    public void start() throws IOException {
        logger.log("Listening on " + port);
        logger.log("Serving resources at " + directory);
        ServerSocket listener = new ServerSocket(port);

        while(true) {
            logger.log("Accepting connections.....");
            Socket io = listener.accept();
            logger.log("Connected\n");


            ReadableSocket reading = new ReadableSocket(io);
            WritableSocket writing = new WritableSocket(io);

            Request request = new RequestParser(reading, logger).read();
            Response response = router.respond(request);

            new ResponseWriter(writing, logger).write(response);

            logger.log("\nClosing connection\n");
            io.close();
        }
    }
}

