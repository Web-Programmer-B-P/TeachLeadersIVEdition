package my.teach.producer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;

public class Consumer<T> implements Runnable {
    private static final Logger LOG = LogManager.getLogger(Consumer.class.getName());
    public static final int MINIMUM_VALUE = 0;
    private final Storage storage;
    private final ArrayList<T> list;

    public Consumer(Storage queue) {
        storage = queue;
        list = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            while (storage.getBufferSize() > MINIMUM_VALUE) {
                list.add((T) storage.get());
            }
        } catch (InterruptedException ie) {
            LOG.error("Ошибка в Consumer блоке", ie);
        }
    }

    public int getSize() {
        return list.size();
    }
}
