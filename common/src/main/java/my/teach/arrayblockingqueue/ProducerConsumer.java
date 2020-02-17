package my.teach.arrayblockingqueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.ArrayBlockingQueue;

public class ProducerConsumer<T> {
    private static final Logger LOG = LogManager.getLogger(ProducerConsumer.class.getName());
    private final ArrayBlockingQueue storage;
    private final int storageSize;

    public ProducerConsumer(int storageSize) {
        this.storageSize = storageSize;
        this.storage = new ArrayBlockingQueue<T>(storageSize, true);
    }

    public void startProducer() {
        new Thread(() -> {
            try {
                for (int index = 0; index < storageSize; index++) {
                    storage.put(index);
                }
            } catch (InterruptedException ine) {
                LOG.error(ine);
            }
        }).start();
    }

    public void startConsumer() {
        new Thread(() -> {
            try {
                while (!storage.isEmpty()) {
                    LOG.info(storage.take());
                    Thread.sleep(500);
                }
            } catch (InterruptedException ine) {
                LOG.error(ine);
            }
        }).start();
    }

    public static void main(String[] args) {
        ProducerConsumer<Integer> producerConsumer = new ProducerConsumer<>(50);
        producerConsumer.startProducer();
        producerConsumer.startConsumer();
        producerConsumer.startConsumer();
        producerConsumer.startConsumer();
        producerConsumer.startConsumer();
    }
}
