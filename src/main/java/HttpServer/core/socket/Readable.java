package HttpServer.core.socket;

import java.io.IOException;

public interface Readable {
    String readLine() throws IOException;
}