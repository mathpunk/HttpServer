package HttpServer;

import java.io.IOException;

public interface Writable {
    void writeLine(String output) throws IOException;

    void flush();
}
