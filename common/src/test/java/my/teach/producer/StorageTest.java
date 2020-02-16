package my.teach.producer;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class StorageTest {
    @Test
    public void whenCheckWorkProducerAndConsumer() throws InterruptedException {
        Storage<Integer> buffer = new Storage<>(1000);
        Thread producer = new Thread(new Producer(buffer, 1000));
        Consumer<Integer> consumerWithBuffer = new Consumer<>(buffer);
        Thread consumer = new Thread(consumerWithBuffer);
        producer.start();
        producer.join();
        consumer.start();
        consumer.join();
        int actual = consumerWithBuffer.getSize();
        int expected = 1000;
        assertThat(actual, is(expected));
    }
}