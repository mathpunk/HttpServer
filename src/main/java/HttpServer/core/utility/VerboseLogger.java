package HttpServer.core.utility;

public class VerboseLogger implements Logger {
    public void log(String string) {
        System.out.println(string);
    }
}
