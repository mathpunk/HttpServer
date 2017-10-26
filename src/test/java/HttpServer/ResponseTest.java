package HttpServer;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ResponseTest {

    @Test
    public void itReadsAndWritesStatuses() {
        Response response = new Response();
        response.setStatus(200);
        assertEquals(response.getStatus(), 200);
    }

    @Test
    public void itReadsAndWritesNotFoundStatus() {
        Response response = new Response();
        response.setStatus(404);
        assertEquals(response.getStatus(), 404);
    }

    @Test
    public void itWritesAndReadsBody() {
        Response response = new Response();
        response.putBody("I am a response");
        assertEquals(response.getBody(), "I am a response");
    }

    @Test
    public void itWritesAndReadsArbitraryHeaders() {
        Response response = new Response();
        response.putHeader("Content Length", "0");
        assertEquals(response.getHeader("Content Length"), "0");
    }

    @Test
    public void itWritesAndReadsAVersion() {
        Response response = new Response();
        String versionSupported = "HTTP 1/1.1";
        response.putVersion(versionSupported);
        assertEquals(versionSupported, response.getVersion());
    }

//    Reading request:
//    --------------------------
//    PUT /file1 HTTP/1.1
//    Content-Length: 0
//    Host: localhost:5000
//    Connection: Keep-Alive
//    User-Agent: Apache-HttpClient/4.3.5 (java 1.5)
//    Accept-Encoding: gzip,deflate
//
//    Writing response:
//            --------------------------
//    Exception in thread "main" java.lang.NullPointerException
//    at HttpServer.Response.getStatusLine(Response.java:79)
//    at HttpServer.Response.streamHeaders(Response.java:53)
//    at HttpServer.Response.streamHead(Response.java:59)
//    at HttpServer.Response.streamResponse(Response.java:67)
//    at HttpServer.ResponseWriter.write(ResponseWriter.java:20)
//    at HttpServer.SynchronousListener.start(SynchronousListener.java:38)
//    at HttpServer.Server.main(Server.java:17)


}
