package my.teach;

import my.teach.server.HttpServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StartHttpServer {
    private static final Logger LOG = LogManager.getLogger(HttpServer.class.getName());
    public static void main(String[] args) {
        try {
            new HttpServer(Integer.parseInt(args[0])).start();
        } catch (NumberFormatException nfe) {
            LOG.info("You must check your port argument, it must be a number, for example: java -jar http.jar 33333");
        }
    }
}
