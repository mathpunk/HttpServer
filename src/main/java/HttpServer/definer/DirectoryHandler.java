package HttpServer.definer;

import HttpServer.request.Request;
import HttpServer.response.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

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
            respondWithFileContent(file, response);
        } else {
            response.setStatus(404);
        }
        System.out.println(response.getBody());
        return response;
    }

    private void respondWithFileContent(File file, Response response) {
        response.setStatus(200);
        try {
            StringBuilder content = getFileContent(file);
            response.setBody(content.toString().trim());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private StringBuilder getFileContent(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        Stream<String> lines = Files.lines(Paths.get(file.getAbsolutePath()));
        lines.forEach(line -> content.append(line).append("\n"));
        lines.close();
        return content;
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
