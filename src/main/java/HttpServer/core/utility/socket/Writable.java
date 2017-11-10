package HttpServer.core.utility.socket;

import java.io.IOException;

public interface Writable {
    void writeLine(String output) throws IOException;

    void writeByte(byte bite) throws IOException;

    void flush();

    void close();
}
