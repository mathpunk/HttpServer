package HttpServer.core.responder.service;

import HttpServer.core.request.Request;
import HttpServer.core.response.Response;
import HttpServer.core.utility.MediaTypeChecker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class DirectoryService implements Service {

    private final File directory;
    private final MediaTypeChecker typeChecker;

    public DirectoryService(String directoryPath) {
        this.typeChecker = new MediaTypeChecker();
        this.directory = new File(directoryPath);
    }

    @Override
    public Response respond(Request request) {
        Response response = new Response();
        if (request.getUriString().equals("/")) {
            response = respondWithDirectoryContents(response);
        } else {
            File file = getFile(request.getUriString());
            if (file.exists()) {
                response = respondWithFileData(file, response);
            } else {
                response.setStatus(404);
            }
        }
        return response;
    }

    private Response respondWithDirectoryContents(Response response) {
        response.setStatus(200);
        StringBuilder content = new StringBuilder();
        for (String filename : fileNames()) {
            content.append("<a href=/" + filename + ">" + filename + "</a>\n");
            response.setBody(content.toString());
        }
        return response;
    }

    private Response respondWithFileData(File file, Response response) {
        response.setStatus(200);
        try {
            StringBuilder content = getFileContent(file);
            response.setBody(content.toString().trim());
            response.setHeader("Content-Type", typeChecker.typeFile(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
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
