package HttpServer.definer;

import HttpServer.router.Router;

import java.io.File;
import java.util.ArrayList;

public class FileRouteDefiner implements IRouteDefiner {

    private final String directoryPath;
    private final File directory;
    private Router router;

    public FileRouteDefiner(String directoryPath, Router blankRouter) {
        this.router = new Router();
        this.directoryPath = directoryPath;
        this.directory = new File(directoryPath);
        addRoutes();
    }

    private void addRoutes() {
        DirectoryHandler dirHandler = new DirectoryHandler("./cob_spec/public");
        ArrayList<String> filenames = listFileNames();
        for (String filename : filenames) {
            String uri = "/" + filename;
            router.defineRoute(uri, "GET", dirHandler);
        }
    }

    @Override
    public Router getRouter() {
        return router;
    }

    public File getDirectory() {
        return directory;
    }

    public ArrayList<String> listFileNames() {
        File[] files = directory.listFiles();
        ArrayList<String> fileNames = new ArrayList<>();
        for (File file : files) {
           fileNames.add(file.getName());
        }
        return fileNames;
    }
}
