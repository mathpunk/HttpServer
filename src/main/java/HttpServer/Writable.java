package HttpServer;

import java.io.IOException;

public interface Writable {
    public void writeLine(String output) throws IOException;
}
