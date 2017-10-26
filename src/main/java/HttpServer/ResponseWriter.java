package HttpServer;

import java.io.IOException;

public class ResponseWriter {

    private Writable client;
    private LoggerInterface logger;

    public ResponseWriter(Writable client, LoggerInterface logger) {
        this.logger = logger;
        this.client = client;
    }

    public void write(Response response) throws IOException {
        logger.log("\nWriting response:");
        logger.log("--------------------------");
        String line = response.getStatusLine();
        client.writeLine(line);
        client.flush();
        client.close();
//        response.streamResponse().forEach(line -> {
//            try {
//                writing.writeLine(line);
//                logger.log(line);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
    }
}
