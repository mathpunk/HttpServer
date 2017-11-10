package HttpServer.core.utility.socket;

import java.io.IOException;

public interface Writable {
    void writeLine(String output) throws IOException;

    void writeBytes(byte[] bytes) throws IOException;

    void flush() throws IOException;

    void close() throws IOException;
}
