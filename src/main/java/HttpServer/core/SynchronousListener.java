package HttpServer.core;

import HttpServer.core.definer.IRouteDefiner;
import HttpServer.core.request.Request;
import HttpServer.core.request.RequestParser;
import HttpServer.core.response.Response;
import HttpServer.core.response.ResponseWriter;
import HttpServer.core.router.Router;
import HttpServer.core.socket.ReadableSocket;
import HttpServer.core.socket.WritableSocket;
import HttpServer.core.utility.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SynchronousListener {

    private final Logger logger;
    private final int port;
    private final String directory;
    private final IRouteDefiner controller;
    private final Router router;

    public SynchronousListener(int port, String directory, IRouteDefiner controller, Logger logger) {
        this.port = port;
        this.directory = directory;
        this.logger = logger;
        this.controller = controller;
        this.router = controller.getRouter();
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

            Response response = router.route(request);

            new ResponseWriter(writing, logger).write(response);

            logger.log("\nClosing connection\n");
            io.close();
        }
    }
}

