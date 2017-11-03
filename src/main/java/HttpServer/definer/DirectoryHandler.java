package HttpServer.definer;

import HttpServer.request.Request;
import HttpServer.response.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DirectoryHandler implements Handler {

    private final File directory;

    public DirectoryHandler(String directoryPath) {
        this.directory = new File(directoryPath);
    }

    @Override
    public Response respond(Request request) {
        File file = getFile(request.getUri());
        Response response = new Response();
        if (file.exists()) {
            response.setStatus(200);
            try {
                String contents = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                response.setBody(contents);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            response.setStatus(404);
        }
        return response;
    }

    private File getFile(String uri) {
        String pathName = directory.getAbsolutePath() + uri;
        Path path = Paths.get(pathName);
        return new File(String.valueOf(path));
    }

    public ArrayList<String> fileNames() {
        File[] files = directory.listFiles();
        ArrayList<String> names = new ArrayList<>();
        for (File file : files) {
            names.add(file.getName());
        }
        return names;
    }
}
