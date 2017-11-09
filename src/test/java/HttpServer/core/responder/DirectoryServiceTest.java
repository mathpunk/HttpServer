package HttpServer.core.service;

import HttpServer.core.request.Request;
import HttpServer.core.response.Response;
import org.junit.Before;
import org.junit.Test;

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
    public void it404sIfFileDoesNotExist() {
        Request request = new Request();
        request.setUri("/i-dont-have-this-file");
        request.setMethod("GET");
        Response response = directoryService.respond(request);
        assertEquals(404, response.getStatus());
    }

    @Test
    public void itIsOkIfFileExists() {
        Response response = directoryService.respond(fileRequest);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void itRespondsToGetWithBodySetToContent() {
        Response response = directoryService.respond(fileRequest);
        assertEquals("file1 contents", response.getBody());
    }

    @Test
    public void itRespondsWithContentTypeSet() {
        Response response = directoryService.respond(fileRequest);
        assertEquals("application/octet-stream", response.getHeader("Content-Type"));
    }

    @Test
    public void itRespondsToRootUriWithFilenameList() {
        Request request = new Request("/", "GET");
        Response response = directoryService.respond(request);
        assertThat(response.getBody(), containsString("file1"));
        assertThat(response.getBody(), containsString("partial_content.txt"));
    }

}
