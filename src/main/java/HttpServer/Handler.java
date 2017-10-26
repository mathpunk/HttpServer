package HttpServer;

@FunctionalInterface
public interface Handler {
    public Response apply(Request request);
}
