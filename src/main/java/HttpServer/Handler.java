package HttpServer;

@FunctionalInterface
public interface Handler {
    Response apply(Request request);
}
