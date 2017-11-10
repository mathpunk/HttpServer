package HttpServer.core.responder.service;

import HttpServer.core.message.request.Request;
import HttpServer.core.message.response.Response;
import HttpServer.core.utility.MediaTypeChecker;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DirectoryService implements Service {

    private final File directory;
    private final MediaTypeChecker typeChecker;

    public DirectoryService(String directoryPath) {
        this.typeChecker = new MediaTypeChecker();
        this.directory = new File(directoryPath);
    }

    @Override
    public Response respond(Request request) throws IOException {
        Response response = new Response();
        String uri = request.getUriString();
        if (uri.equals("/")) {
            response = respondWithDirectoryContents(response);
        } else {
            File file = getFile(uri);
            if (!file.exists()) {
                response.setStatus(404);
            } else {
                if (request.getHeader("Range") != null) {
                    response = respondWithFileRange(response, file);
                } else {
                    response = respondWithEntireFileContent(response, file);
                }
            }
        }
        return response;
    }

    private Response respondWithEntireFileContent(Response response, File file) throws IOException {
        response.setStatus(200);
        response.setHeader("Content-Type", typeChecker.typeFile(file));
        byte[] bytes = Files.readAllBytes(file.toPath());
        response.setHeader("Content-Length", bytes.length);
        String content = new String(bytes, Charset.forName("UTF-8"));
        response.setBody(content);
        return response;
    }

    private Response respondWithFileRange(Response response, File file) throws IOException {
        response.setStatus(206);
        // respond with partial data
        response.setHeader("Content-Type", typeChecker.typeFile(file));
        byte[] bytes = Files.readAllBytes(file.toPath());
        response.setHeader("Content-Length", bytes.length);
        String content = new String(bytes, Charset.forName("UTF-8"));
        response.setBody(content);
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
