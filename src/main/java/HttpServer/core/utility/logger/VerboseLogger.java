package HttpServer.core.utility.logger;

public class VerboseLogger implements Logger {
    public void log(String string) {
        System.out.println(string);
    }
}
