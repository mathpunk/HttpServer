package HttpServer.socket;

import java.io.IOException;

public interface Writable {
    void writeLine(String output) throws IOException;

    void flush();

    void close();
}
