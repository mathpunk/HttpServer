package HttpServer.core;

import HttpServer.core.request.Request;
import HttpServer.core.request.RequestParser;
import HttpServer.core.response.Response;
import HttpServer.core.response.ResponseWriter;
import HttpServer.core.router.Router;
import HttpServer.core.utility.logger.Logger;
import HttpServer.core.utility.socket.ReadableSocket;
import HttpServer.core.utility.socket.WritableSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AsynchronousListener {

    private final Logger logger;
    private final int port;
    private final String directory;
    private final Router router;

    public AsynchronousListener(int port, String directory, Router router, Logger logger) {
        this.port = port;
        this.directory = directory;
        this.logger = logger;
        this.router = router;
    }

    public void start() throws IOException {
        logger.log("Listening on " + port);
        logger.log("Serving resources at " + directory);
        ServerSocket listener = new ServerSocket(port);

        while(true) {
            logger.log("Accepting connections.....");
            Socket io = listener.accept();
            Runnable connection = new Connection(router, io, logger);
            Thread process = new Thread(connection);
            process.start();
        }
    }

}
