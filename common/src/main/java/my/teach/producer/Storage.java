package my.teach.producer;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class Storage<T> {
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private int sizeStorage;

    public Storage(int sizeStorage) {
        this.sizeStorage = sizeStorage;
    }

    public synchronized void put(T value) throws InterruptedException {
        while (queue.size() == sizeStorage) {
            this.wait();
        }
        queue.add(value);
        this.notifyAll();
    }

    public synchronized T get() throws InterruptedException {
        while (queue.size() == 0) {
            this.wait();
        }
        T res = queue.remove();
        this.notifyAll();
        return res;
    }

    public synchronized int getBufferSize() {
        return queue.size();
    }
}
