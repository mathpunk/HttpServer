package HttpServer.definer;

import HttpServer.Resource;
import HttpServer.response.Response;
import HttpServer.router.Router;
import HttpServer.router.Routes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileRouteDefiner implements IRouteDefiner {

    private final String directoryPath;
    private final File directory;
    private Router router;

    public FileRouteDefiner(String directoryPath) {
        this.router = new Router(new Routes());
        this.directoryPath = directoryPath;
        this.directory = new File(directoryPath);
        serveAllFiles();
    }

    private void serveAllFiles() {
        Resource firstFile = new Resource(new Object());
        firstFile.setUri("/file1");
        Resource secondFile = new Resource(new Object());
        secondFile.setUri("/text-file.txt");

        RequestHandler okHandler = new RequestHandler((whatever) -> new Response().setStatus(200));
        firstFile.setHandler("GET", okHandler);
        secondFile.setHandler("GET", okHandler);

        Resource[] iterator = {firstFile, secondFile};
        for (Resource resource : iterator) {
            router.defineRoute(resource.getUri(), "GET", okHandler);
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

    public String getFileContent(String filename) throws IOException {
        File file = new File(directoryPath, filename);
        return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
    }
}
