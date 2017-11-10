package HttpServer.core.utility;

import HttpServer.core.message.Uri;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClauseTest {

    private Clause simpleClause;

    @Before
    public void setup() {
        String uriString = "/";
        String method = "GET";
        simpleClause = new Clause(uriString, method);
    }

    @Test
    public void itGetsAUriString() {
        assertEquals("/", simpleClause.getUriString());
    }

    @Test
    public void itGetsAMethod() {
       assertEquals("GET", simpleClause.getMethod());
    }

    @Test
    public void itGetsAUri() {
        Uri uri = new Uri("/");
        Clause clause = new Clause(uri, "HEAD");
        assertEquals(uri, clause.getUri());
    }

}
