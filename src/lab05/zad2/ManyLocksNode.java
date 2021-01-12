package lab05.zad2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ManyLocksNode implements INode {
    private Object value;
    private final Lock nodeLock;
    private ManyLocksNode next;
    private static long SLEEP_TIME;


    public ManyLocksNode(Object value, ManyLocksNode next) {
        this.value = value;
        this.next = next;
        this.nodeLock = new ReentrantLock();
    }

    @Override
    public boolean contains(Object o) throws InterruptedException {
        if (o == null)
            return false;

        ManyLocksNode first = this, prev;
        first.nodeLock.lock();

        while (first != null) {
            if (first.value == o) {
                Thread.sleep(SLEEP_TIME / 5);
                first.nodeLock.unlock();
                return true;
            }

            prev = first;
            first = first.next;
            if (first != null)
                first.nodeLock.lock();
            prev.nodeLock.unlock();
        }

        return false;
    }

    @Override
    public ManyLocksNode remove(Object o) throws InterruptedException {
        ManyLocksNode first = this, prev = null, prevPrev;
        first.nodeLock.lock();

        while (first != null) {
            if (first.value == o) {
                Thread.sleep(SLEEP_TIME / 3);
                if (this == first) {
                    first = this.next;
                    this.next = null;
                    this.nodeLock.unlock();
                    return first;
                }

                prev.next = first.next;
                prev.nodeLock.unlock();
                first.next = null;
                first.nodeLock.unlock();
                return this;
            }

            prevPrev = prev;
            prev = first;
            first = first.next;
            if (first != null)
                first.nodeLock.lock();
            if (prevPrev != null)
                prevPrev.nodeLock.unlock();
        }

        prev.nodeLock.unlock();
        return this;
    }

    @Override
    public boolean add(Object o) throws InterruptedException {
        if (o == null)
            return false;

        ManyLocksNode first = this, second = this.next;
        first.nodeLock.lock();

        while (second != null) {
            second.nodeLock.lock();
            first.nodeLock.unlock();
            first = second;
            second = second.next;
        }
        Thread.sleep(SLEEP_TIME / 2);
        second = new ManyLocksNode(o, null);
        first.next = second;
        first.nodeLock.unlock();

        return true;
    }

    @Override
    public void print() {
        ManyLocksNode first = this;
        while (first != null) {
            System.out.println(first.value);
            first = first.next;
        }
    }

    public static void setSleepTime(long sleepTime) {
        SLEEP_TIME = sleepTime;
    }
}
