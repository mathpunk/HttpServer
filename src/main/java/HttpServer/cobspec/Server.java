package HttpServer.cobspec;

import HttpServer.core.AsynchronousListener;
import HttpServer.core.responder.*;
import HttpServer.core.responder.service.*;
import HttpServer.core.router.Router;
import HttpServer.core.utility.CommandLineParser;
import HttpServer.core.utility.logger.Logger;
import HttpServer.core.utility.logger.QuietLogger;

import java.util.HashMap;

public class Server {
    public static void main(String[] args) throws Exception {
        int port = 1337;
        String directory = null;
        // Logger logger = new VerboseLogger();
        Logger logger = new QuietLogger();

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
        Responder okResponder = new FunctionalResponder(200);
        // router.defineRoute("/", "GET", okResponder);
        router.defineRoute("/", "HEAD", okResponder);

        // SimplePut, SimplePost
        router.defineRoute("/form", "PUT", okResponder);
        router.defineRoute("/form", "POST", okResponder);

        // FourEightTeen
        Service teaHandler = new TeaService();
        router.defineRoute("/tea", "GET", teaHandler);
        router.defineRoute("/coffee", "GET", teaHandler);

        // RedirectPath
        HashMap<String, String> redirectionMap = new HashMap<>();
        redirectionMap.put("/redirect", "/");
        Responder redirectHandler = new RedirectService(redirectionMap);
        router.defineRoute("/redirect", "GET", redirectHandler);

        // SimpleOption
        router.defineRoute("/method_options", "GET", okResponder);
        router.defineRoute("/method_options", "HEAD", okResponder);
        router.defineRoute("/method_options", "POST", okResponder);
        router.defineRoute("/method_options", "OPTIONS", okResponder);
        router.defineRoute("/method_options", "PUT", okResponder);
        router.defineRoute("/method_options2", "GET", okResponder);
        router.defineRoute("/method_options2", "OPTIONS", okResponder);

        // ParameterDecode
        router.defineRoute("/parameters", "GET", new ParameterService());

        // FileContents, MethodNotAllowed
        DirectoryService directoryService = new DirectoryService("cob_spec/public");
        for (String filename : directoryService.fileNames()) {
            router.defineRoute("/" + filename, "GET", directoryService);
        }

        // CookieData
        Responder baker = new CookieService();
        router.defineRoute("/eat_cookie", "GET", baker);
        router.defineRoute("/cookie", "GET", baker);

        // Directory Listing
        router.defineRoute("/", "GET", directoryService);
    }
}
