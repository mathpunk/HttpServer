package HttpServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SynchronousListener {

    private final LoggerInterface logger;
    private final Controller controller;
    private final int port;
    private final String directory;

    public SynchronousListener(int port, String directory, LoggerInterface logger) {
        this.port = port;
        this.directory = directory;
        this.logger = logger;
        this.controller = new Controller();
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
            Response response = controller.respond(request);

            new ResponseWriter(writing, logger).write(response);

            logger.log("\nClosing connection\n");
            io.close();
        }
    }
}

