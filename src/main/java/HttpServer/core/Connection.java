package HttpServer.core;

import HttpServer.core.message.request.Request;
import HttpServer.core.message.request.RequestParser;
import HttpServer.core.message.response.Response;
import HttpServer.core.message.response.ResponseWriter;
import HttpServer.core.router.Router;
import HttpServer.core.utility.logger.Logger;
import HttpServer.core.utility.socket.ReadableSocket;
import HttpServer.core.utility.socket.WritableSocket;

import java.io.IOException;
import java.net.Socket;

public class Connection implements Runnable {
    private final Logger logger;
    private final Router router;
    private Socket io;

    public Connection(Router router, Socket io, Logger logger) {
        this.router = router;
        this.logger = logger;
        this.io = io;
    }

    public void run() {
        logger.log("Connected");
        logger.log("Using socket: " + io.toString());
        try {
            ReadableSocket reading = new ReadableSocket(io);
            WritableSocket writing = new WritableSocket(io);
            Request request = new RequestParser(reading, logger).read();
            Response response = router.route(request);
            new ResponseWriter(writing, logger).write(response);
            logger.log("\nClosing connection\n");
            io.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
