package HttpServer;

import java.io.IOException;

public class ResponseWriter {

    private Writable writing;
    private LoggerInterface logger;
    private Response response;

    public ResponseWriter(Response response, Writable writing, LoggerInterface logger) {
        this.logger = logger;
        this.response = response;
        this.writing = writing;
    }

    public void write() throws IOException {
        logger.log("\nWriting response:");
        logger.log("--------------------------");
        response.streamResponse().forEach(line -> {
            try {
                writing.writeLine(line);
                logger.log(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writing.flush();
        writing.close();
    }

}
