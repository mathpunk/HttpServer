package HttpServer.core.utility;

import java.io.File;
import java.util.HashMap;

public class MediaTypeChecker {
    private HashMap<String, String> types;

    public MediaTypeChecker() {
        this.types = new HashMap<String, String>();
        types.put("jpeg", "image/jpeg");
        types.put("png", "image/png");
        types.put("gif", "image/gif");
        types.put("txt", "text/plain");
    }

    public String typeExtension(String extension) {
        String type = types.get(extension);
        if (type == null) {
            return "application/octet-stream";
        } else {
            return type;
        }
    }

    public String typeFilename(String filename) {
        String separator = "\\.";
        String[] tokens = filename.split(separator);
        String extension;
        if (tokens.length == 1) {
            extension = "";
        } else {
            extension = tokens[tokens.length-1];
        }
        return typeExtension(extension);
    }

    public String typeFile(File file) {
        String filename = file.getName();
        return typeFilename(filename);
    }
}
