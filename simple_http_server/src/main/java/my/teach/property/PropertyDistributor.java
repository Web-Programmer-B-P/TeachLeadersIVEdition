package my.teach.property;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyDistributor {
    private final static Logger LOG = LogManager.getLogger(PropertyDistributor.class.getName());
    private int portNumber;
    private int bufferSize;
    private String folderPath;

    public PropertyDistributor() {
        loadProperties();
    }

    public void loadProperties() {
        Properties config = new Properties();
        try (InputStream inputStream = PropertyDistributor.class.getClassLoader().getResourceAsStream("http.properties")) {
            config.load(inputStream);
            portNumber = Integer.parseInt(config.getProperty("http.port"));
            folderPath = config.getProperty("http.resourcePath");
            bufferSize = Integer.parseInt(config.getProperty("http.bufferSize"));
        } catch (IOException io) {
            LOG.error(io);
        }
    }

    public int getPortNumber() {
        return portNumber;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public int getBufferSize() {
        return bufferSize;
    }
}
