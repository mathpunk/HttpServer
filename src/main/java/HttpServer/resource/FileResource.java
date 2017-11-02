package HttpServer.resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileResource {
    private final String uri;
    private final File file;

    public FileResource(File file) {
        this.file = file;
        this.uri = "/" + file.getName();
    }

    public String getUri() {
        return this.uri;
    }

    public String get() throws IOException {
        return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
    }
}
