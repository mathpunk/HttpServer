package HttpServer.core.responder.service;

import HttpServer.core.message.request.Request;
import HttpServer.core.message.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;

public class DirectoryServiceTest {

    private DirectoryService directoryService;
    private Request fileRequest;

    @Before
    public void setup() {
        this.directoryService = new DirectoryService("cob_spec/public");
        Request fileRequest = new Request();
        fileRequest.setUri("/file1");
        fileRequest.setMethod("GET");
        this.fileRequest = fileRequest;
    }

    @Test
    public void itListsFilenamesServed() {
        ArrayList<String> names = directoryService.fileNames();
        assertThat(names, hasItems( "file1", "file2", "image.gif",
                "image.jpeg", "image.png", "partial_content.txt",
                "patch-content.txt", "text-file.txt" ));
    }

    @Test
    public void it404sIfFileDoesNotExist() throws IOException {
        Request request = new Request();
        request.setUri("/i-dont-have-this-file");
        request.setMethod("GET");
        Response response = directoryService.respond(request);
        assertEquals(404, response.getStatus());
    }

    @Test
    public void itIsOkIfFileExists() throws IOException {
        Response response = directoryService.respond(fileRequest);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void itRespondsToGetWithBodySetToContent() throws IOException {
        Response response = directoryService.respond(fileRequest);
        assertEquals("file1 contents", response.getBodyAsString());
    }

    @Test
    public void itSurvivesARequestForImageContent() throws IOException {
        Request request = new Request("/image.jpeg", "GET");
        directoryService.respond(request);
        assert(true);
    }

    @Test
    public void itRespondsWithContentTypeSet() throws IOException {
        Response response = directoryService.respond(fileRequest);
        assertEquals("application/octet-stream", response.getHeader("Content-Type"));
    }

    @Test
    public void itRespondsToRootUriWithFilenameList() throws IOException {
        Request request = new Request("/", "GET");
        Response response = directoryService.respond(request);
        assertThat(response.getBodyAsString(), containsString("file1"));
        assertThat(response.getBodyAsString(), containsString("partial_content.txt"));
    }

    @Test
    public void itResponseToRangedRequestsWith206() throws IOException {
        Request request = new Request("/partial_content.txt", "GET");
        request.setHeader("Range", "bytes=0-4");
        Response response = directoryService.respond(request);
        assertEquals(206, response.getStatus());
    }

    @Test
    public void rangedRequestsIncludeTheRequestedData() throws IOException {
        Request request = new Request("/partial_content.txt", "GET");
        request.setHeader("Range", "bytes=0-4");
        Response response = directoryService.respond(request);
        assertThat(response.getBodyAsString(), containsString("This "));
    }

    @Test
    public void rangedRequestsIncludeOnlyTheRequestedData() throws IOException {
        Request request = new Request("/partial_content.txt", "GET");
        request.setHeader("Range", "bytes=0-4");
        Response response = directoryService.respond(request);
        byte[] bodyInBytes = response.getBodyAsString().getBytes(Charset.forName("UTF-8"));
        assertEquals(5, bodyInBytes.length);
    }

    @Test
    public void rangedRequestsSetContentLength() throws IOException {
        Request request = new Request("/partial_content.txt", "GET");
        request.setHeader("Range", "bytes=0-4");
        Response response = directoryService.respond(request);
        assertEquals("5", response.getHeader("Content-Length"));
    }

    @Test
    public void boundedOnlyAboveHasContentToTheEnd() throws IOException {
        Request request = new Request("/partial_content.txt", "GET");
        request.setHeader("Range", "bytes=-6");
        Response response = directoryService.respond(request);
        String body = response.getBodyAsString();
        assertEquals(" 206.\n", body);
    }

    @Test
    public void boundedOnlyAboveHasLengthEqualToUpperBound() throws IOException {
        Request request = new Request("/partial_content.txt", "GET");
        request.setHeader("Range", "bytes=-6");
        Response response = directoryService.respond(request);
        String body = response.getBodyAsString();
        assertEquals(6, body.getBytes().length);
    }

    @Test
    public void thisStringHasSixUTF8Bytes() {
        String theEnd = " 206.\n";
        assertEquals(6, theEnd.getBytes().length);
    }
}