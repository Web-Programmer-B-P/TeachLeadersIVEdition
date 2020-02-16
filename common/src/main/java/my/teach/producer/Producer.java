package my.teach.producer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Producer implements Runnable {
    private static final Logger LOG = LogManager.getLogger(Producer.class.getName());
    private final Storage storage;
    private final int limit;

    public Producer(Storage queue, int limit) {
        storage = queue;
        this.limit = limit;
    }

    @Override
    public void run() {
        int index = 0;
        while (index < limit) {
            try {
                storage.put(index++);
            } catch (InterruptedException ie) {
                LOG.error("Что то не так пошло в Producer нити", ie);
            }
        }
    }
}
