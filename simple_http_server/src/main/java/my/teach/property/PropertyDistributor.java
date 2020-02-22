package my.teach.property;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class PropertyDistributor {
    private static Logger LOGGER = Logger.getLogger(PropertyDistributor.class.getName());
    private int bufferSize;

    public PropertyDistributor() {
        loadProperties();
    }

    public void loadProperties() {
        try (InputStream inputStream = PropertyDistributor.class.getClassLoader().getResourceAsStream("http.properties")) {
            LogManager.getLogManager().readConfiguration(inputStream);
            bufferSize = Integer.parseInt(LogManager.getLogManager().getProperty("http.bufferSize"));
        } catch (IOException io) {
            LOGGER.log(Level.SEVERE, "Ошибка при чтении пропертей", io);
        }
    }

    public int getBufferSize() {
        return bufferSize;
    }
}
