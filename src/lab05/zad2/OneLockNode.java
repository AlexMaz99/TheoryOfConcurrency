package lab5.zad2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OneLockNode implements INode {
    private Object value;
    public static final Lock LOCK = new ReentrantLock();
    private OneLockNode next;
    private static long SLEEP_TIME;

    public OneLockNode(Object value, OneLockNode next) {
        this.value = value;
        this.next = next;
    }

    @Override
    public boolean contains(Object o) throws InterruptedException {
        if (o == null)
            return false;

        OneLockNode first = this;
        LOCK.lock();

        while (first != null) {
            if (first.value == o) {
                Thread.sleep(SLEEP_TIME / 5);
                LOCK.unlock();
                return true;
            }
            first = first.next;
        }

        LOCK.unlock();
        return false;
    }

    @Override
    public OneLockNode remove(Object o) throws InterruptedException {
        OneLockNode first = this, prev = null;
        LOCK.lock();

        while (first != null) {
            if (first.value == o) {
                Thread.sleep(SLEEP_TIME / 3);
                if (this == first) {
                    first = this.next;
                    this.next = null;
                    LOCK.unlock();
                    return first;
                }

                prev.next = first.next;
                first.next = null;
                LOCK.unlock();
                return this;
            }
            prev = first;
            first = first.next;
        }

        LOCK.unlock();
        return this;
    }

    @Override
    public boolean add(Object o) throws InterruptedException {
        if (o == null)
            return false;

        OneLockNode first = this, second = this.next;
        LOCK.lock();

        while (second != null) {
            first = second;
            second = second.next;
        }
        Thread.sleep(SLEEP_TIME / 2);
        second = new OneLockNode(o, null);
        first.next = second;
        LOCK.unlock();

        return true;
    }

    @Override
    public void print() {
        OneLockNode first = this;
        while (first != null) {
            System.out.println(first.value);
            first = first.next;
        }
    }

    public static void setSleepTime(long sleepTime) {
        SLEEP_TIME = sleepTime;
    }
}
