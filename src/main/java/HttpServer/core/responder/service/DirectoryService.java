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
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.US_ASCII;

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
        if (request.getHeader("Range") != null) {
            response.setStatus(206);
        } else {
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
            response.setHeader("Content-Type", typeChecker.typeFile(file));
            String body = getFileContent(file);
            response.setBody(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private String getFileContent(File file) throws IOException {
        byte[] bytes = Files.readAllBytes(file.toPath());
        String content = new String (bytes, Charset.forName("UTF-8"));
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
