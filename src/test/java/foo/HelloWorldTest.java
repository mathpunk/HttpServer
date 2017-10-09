package foo;

import org.junit.Test;

import static org.junit.Assert.*;

public class HelloWorldTest {
    @Test
    public void messageIsHelloWorld() {

        String actual = Message.getMessage();
        assertEquals("Hello World", actual);
    }

    @Test
    public void messageIsGoodBye() {

        //String actual = Message.getMessage();
        //assertEquals("Good bye", actual);
    }
}