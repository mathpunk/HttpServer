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
import java.util.Arrays;

public class DirectoryService implements Service {

    private final File directory;
    private final MediaTypeChecker typeChecker;

    public DirectoryService(String directoryPath) {
        this.typeChecker = new MediaTypeChecker();
        this.directory = new File(directoryPath);
    }

    @Override
    public Response respond(Request request) throws IOException {
        String uri = request.getUriString();
        if (uri.equals("/")) {
            return respondWithDirectoryListing();
        } else {
            File file = getFile(uri);
            if (!file.exists()) {
                return new Response(404);
            } else {
                String rangeRequestString = request.getHeader("Range");
                if (rangeRequestString == null) {
                    return respondWithFullContent(file);
                } else {
                    Response response = new Response(206);
                    response.setHeader("Content-Type", typeChecker.typeFile(file));
                    byte[] bytes = Files.readAllBytes(file.toPath());

                    String unit = rangeRequestString.split("=")[0];
                    String rangeRequested = rangeRequestString.split("=")[1];
                    StringDefinedInterval requestedInterval = new StringDefinedInterval(rangeRequested);

                    if (requestedInterval.lower == null && requestedInterval.upper != null) {
                        return respondCountingFromEndOfFile(response, bytes, requestedInterval);
                    } else {
                        return respondFromBeginningOfFile(response, bytes, requestedInterval);
                    }
                }
            }
        }
    }

    private Response respondCountingFromEndOfFile(Response response, byte[] bytes, StringDefinedInterval requestedInterval) {
        byte[] requestedBytes = bytesFromTheEnd(bytes, requestedInterval);
        response.setHeader("Content-Length", requestedBytes.length);
        String content = new String(requestedBytes, Charset.forName("UTF-8"));
        response.setBody(content);
        return response;
    }

    private Response respondFromBeginningOfFile(Response response, byte[] bytes, StringDefinedInterval requestedInterval) {
        int upper = (requestedInterval.upper == null) ? bytes.length : requestedInterval.upper + 1;
        int lower = (requestedInterval.lower == null) ? 0 : requestedInterval.lower;
        byte[] requestedBytes = Arrays.copyOfRange(bytes, lower, upper);
        response.setHeader("Content-Length", requestedBytes.length);
        String content = new String(requestedBytes, Charset.forName("UTF-8"));
        response.setBody(content);
        return response;
    }

    private byte[] bytesFromTheEnd(byte[] bytes, StringDefinedInterval requestedInterval) {
        int requestedLength = requestedInterval.length();
        int approximatelyWhereWeStart = bytes.length - requestedLength;
        return Arrays.copyOfRange(bytes, approximatelyWhereWeStart, bytes.length);
    }

    private Response respondWithFullContent(File file) throws IOException {
        Response response;
        byte[] bytes = Files.readAllBytes(file.toPath());
        response = new Response(200);
        response.setHeader("Content-Type", typeChecker.typeFile(file));
        response.setHeader("Content-Length", bytes.length);
        String content = new String(bytes, Charset.forName("UTF-8"));
        response.setBody(content);
        return response;
    }

    private Response respondWithDirectoryListing() {
        Response response = new Response();
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
