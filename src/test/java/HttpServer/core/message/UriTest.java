package HttpServer.core.message;

import HttpServer.core.message.request.Uri;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Set;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class UriTest {

    private Uri uri;
    private String parameteredUri;

    @Before
    public void setup() {
       parameteredUri = "/parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff";
       uri = new Uri(parameteredUri);
    }
    
    @Test
    public void itProvidesAccessToThePath() {
        assertEquals("/parameters", uri.getPath());
    }

    @Test
    public void itProvidesAccessToParameterKeys() {
        HashMap<String, String> parameters =  uri.getParameters();
        Set<String> keys = parameters.keySet();
        assertThat(keys, hasItems("variable_1", "variable_2"));
    }

    @Test
    public void itProvidesAccessToParameters() {
        HashMap<String, String> parameters =  uri.getParameters();
        assertEquals("stuff", parameters.get("variable_2"));
    }

    @Test
    public void itProvidesAccessToTheUriString() {
        assertEquals(parameteredUri, uri.getUriString());
    }

    @Test
    public void itDecodesParameterStrings() {
        HashMap<String, String> parameters =  uri.getParameters();
        assertEquals("Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?" , parameters.get("variable_1"));
    }
}
