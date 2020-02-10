package my.teach;

import my.teach.property.PropertyDistributor;
import my.teach.server.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger LOG = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {
        PropertyDistributor properties = new PropertyDistributor();
        try {
            new Server(properties.getPortNumber(), properties.getBufferSize(), properties.getFolderPath()).start();
        } catch (NumberFormatException nfe) {
            LOG.info("You must check your number of port in properties!");
        }
    }
}
