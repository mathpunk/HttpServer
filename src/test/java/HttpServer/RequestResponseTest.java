package HttpServer;

import org.junit.Test;

import static org.junit.Assert.*;

public class RequestResponseTest {

    @Test
    public void takesAConnection() {
        MockConnection connection = new MockConnection();
        RequestResponse reqRes = new RequestResponse();
        reqRes.getResponse(connection);
    }

//    @Test
//    public void recognizesCompletedHeaders() {
//        MockConnection connection = new MockConnection();
//        RequestResponse reqRes = new RequestResponse();
//        reqRes.getResponse(connection);
//        reqRes.requestOver();
//        assertTrue(reqRes.requestOver());
//    }

}
