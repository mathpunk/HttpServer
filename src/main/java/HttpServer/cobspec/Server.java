package HttpServer.cobspec;

import HttpServer.core.SynchronousListener;
import HttpServer.core.definer.*;
import HttpServer.core.router.Router;
import HttpServer.core.utility.CommandLineParser;
import HttpServer.core.utility.Logger;
import HttpServer.core.utility.VerboseLogger;

import java.util.HashMap;

public class Server {
    public static void main(String[] args) throws Exception {
        int port = 1337;
        String directory = null;
        Logger logger = new VerboseLogger();

        CommandLineParser commandLineParser = new CommandLineParser(args, port, directory, logger).invoke();
        port = commandLineParser.getPort();
        directory = commandLineParser.getDirectory();

        IRouteDefiner definer = new IRouteDefiner() {
            private final Router router;

            {
                Router blankRouter = new Router();
                FileRouteDefiner fileDefiner = new FileRouteDefiner("./cob_spec/public", blankRouter);
                TeaRouteDefiner teaDefiner = new TeaRouteDefiner(fileDefiner.getRouter());
                HashMap<String, String> redirectionMap = new HashMap<String, String>();
                redirectionMap.put("/redirect", "/");
                RedirectDefiner redirectDefiner = new RedirectDefiner(teaDefiner.getRouter(), redirectionMap);
                this.router = redirectDefiner.getRouter();

                // Options tests are a concern of the router, in my opinion. Not set with a definer.
                Handler okHandler = new FunctionalHandler(200);
                router.defineRoute("/method_options", "GET", okHandler);
                router.defineRoute("/method_options", "HEAD", okHandler);
                router.defineRoute("/method_options", "POST", okHandler);
                router.defineRoute("/method_options", "OPTIONS", okHandler);
                router.defineRoute("/method_options", "PUT", okHandler);
                router.defineRoute("/method_options2", "GET", okHandler);
                router.defineRoute("/method_options2", "OPTIONS", okHandler);
                router.defineRoute("/", "GET", new FunctionalHandler(200));
                router.defineRoute("/", "HEAD", new FunctionalHandler(200));
                router.defineRoute("/form", "PUT", new FunctionalHandler(200));
            }

            @Override
            public Router getRouter() {
                return router;
            }

        };

        // Listening. Opens a server socket, accepts a connection, reads/writes, closes the connection. Repeat.
        SynchronousListener listener = new SynchronousListener(port, directory, definer, logger);
        listener.start();
    }

}
