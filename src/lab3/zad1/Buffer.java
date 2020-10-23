package lab3.zad1;

import java.util.ArrayList;
import java.util.List;

public class Buffer {
    private final List<Integer> list = new ArrayList<>();
    private final int capacity;

    public Buffer(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void put(int i) throws InterruptedException {
        while (this.list.size() >= this.capacity) {
            System.out.println("Producer " + i + " waits");
            this.wait();
        }
        System.out.println("Producer puts " + i);
        this.list.add(i);
        this.notifyAll();
    }

    public synchronized int get() throws InterruptedException {
        while (this.list.size() == 0) {
            System.out.println("Consumer waits");
            this.wait();
        }
        int result = this.list.remove(this.list.size() - 1);
        System.out.println("Consumer gets " + result);
        this.notifyAll();
        return result;
    }
}