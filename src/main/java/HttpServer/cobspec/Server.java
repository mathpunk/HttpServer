package HttpServer.cobspec;

import HttpServer.core.AsynchronousListener;
import HttpServer.core.SynchronousListener;
import HttpServer.core.request.Request;
import HttpServer.core.resource.*;
import HttpServer.core.response.Response;
import HttpServer.core.router.Router;
import HttpServer.core.utility.CommandLineParser;
import HttpServer.core.utility.logger.Logger;
import HttpServer.core.utility.logger.VerboseLogger;

import java.util.HashMap;
import java.util.function.Function;

public class Server {
    public static void main(String[] args) throws Exception {
        int port = 1337;
        String directory = null;
        Logger logger = new VerboseLogger();

        CommandLineParser commandLineParser = new CommandLineParser(args, port, directory, logger).invoke();
        port = commandLineParser.getPort();
        directory = commandLineParser.getDirectory();
        Router router = new Router();
        defineRoutes(router);

        // Listening. Opens a server socket, accepts a connection, reads/writes, closes the connection. Repeat.
        AsynchronousListener listener = new AsynchronousListener(port, directory, router, logger);
        listener.start();
    }

    private static void defineRoutes(Router router) {
        // Routes for satisfying cob_spec acceptance test suite.

        // SimpleGet, SimpleHead
        Handler okHandler = new FunctionalHandler(200);
        router.defineRoute("/", "GET", okHandler);
        router.defineRoute("/", "HEAD", okHandler);

        // SimplePut, SimplePost
        router.defineRoute("/form", "PUT", okHandler);
        router.defineRoute("/form", "POST", okHandler);

        // FourEightTeen
        Handler teaHandler = new TeaHandler();
        router.defineRoute("/tea", "GET", teaHandler);
        router.defineRoute("/coffee", "GET", teaHandler);

        // RedirectPath
        HashMap<String, String> redirectionMap = new HashMap<>();
        redirectionMap.put("/redirect", "/");
        Handler redirectHandler = new RedirectHandler(redirectionMap);
        router.defineRoute("/redirect", "GET", redirectHandler);

        // SimpleOption
        router.defineRoute("/method_options", "GET", okHandler);
        router.defineRoute("/method_options", "HEAD", okHandler);
        router.defineRoute("/method_options", "POST", okHandler);
        router.defineRoute("/method_options", "OPTIONS", okHandler);
        router.defineRoute("/method_options", "PUT", okHandler);
        router.defineRoute("/method_options2", "GET", okHandler);
        router.defineRoute("/method_options2", "OPTIONS", okHandler);

        // ParameterDecode
        router.defineRoute("/parameters", "GET", new ParameterHandler());

        // FileContents, MethodNotAllowed
        DirectoryHandler directoryHandler = new DirectoryHandler("cob_spec/public");
        for (String filename : directoryHandler.fileNames()) {
            router.defineRoute("/" + filename, "GET", directoryHandler);
        }

        // CookieData
        Handler baker = new CookieService();
        router.defineRoute("/eat_cookie", "GET", baker);
        router.defineRoute("/cookie", "GET", baker);
    }
}
