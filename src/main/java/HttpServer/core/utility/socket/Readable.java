package HttpServer.core.utility.socket;

import java.io.IOException;

public interface Readable {
    String readLine() throws IOException;
}
