package HttpServer.core.responder.service;

import HttpServer.core.message.request.Request;
import HttpServer.core.message.response.Response;
import HttpServer.core.utility.MediaTypeChecker;
import HttpServer.core.utility.logger.StringDefinedInterval;

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
            Response response1 = new Response();
            response1.setStatus(200);
            StringBuilder content = new StringBuilder();
            for (String filename : fileNames()) {
                content.append("<a href=/" + filename + ">" + filename + "</a>\n");
                response1.setBody(content.toString());
            }
            return response1;
        } else {
            File file = getFile(uri);
            if (!file.exists()) {
                response.setStatus(404);
            } else {
                byte[] bytes = Files.readAllBytes(file.toPath());
                String rangeRequestString = request.getHeader("Range");
                StringDefinedInterval requestedInterval = new StringDefinedInterval(null);

                int statusCode = (rangeRequestString == null) ? 200 : 206;
                response.setStatus(statusCode);
                response.setHeader("Content-Type", typeChecker.typeFile(file));

                if (rangeRequestString != null) {
                    String unit = rangeRequestString.split("=")[0];
                    String rangeRequested = rangeRequestString.split("=")[1];
                    requestedInterval = new StringDefinedInterval(rangeRequested);
                }
                int requestedRangeOffset = (requestedInterval.lower == null) ? 0 : requestedInterval.lower;
                int requestedRangeLength = (requestedInterval.length() == null) ? bytes.length : requestedInterval.length();
                byte[] requestedBytes = new byte[requestedRangeLength];
                for (int index = 0; index < requestedRangeLength; index++) {
                    requestedBytes[index] = bytes[requestedRangeOffset+index];
                }
                response.setHeader("Content-Length", requestedBytes.length);
                String content = new String(requestedBytes, Charset.forName("UTF-8"));
                response.setBody(content);
            }
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
