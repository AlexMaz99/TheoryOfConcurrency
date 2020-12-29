package lab4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Java Concurrency Utilities Buffer
public class JavaConcurrencyBuffer implements IBuffer {
    private final int M; // 2 * M - size of buffer
    private final List<Integer> resources = new ArrayList<>();
    private final Lock lock = new ReentrantLock();
    private final Condition putCondition = lock.newCondition();
    private final Condition getCondition = lock.newCondition();

    public JavaConcurrencyBuffer(int M) {
        this.M = M;
    }

    @Override
    public void put(int[] values) {
        lock.lock();
        while (2 * M - resources.size() < values.length) {
            try {
                putCondition.await();
            } catch (InterruptedException e) {
                lock.unlock();
                Thread.currentThread().interrupt();
                return;
            }
        }

        for (Integer value : values)
            resources.add(value);

        getCondition.signalAll();
        lock.unlock();
    }

    @Override
    public void get(int numberOfValues) {
        lock.lock();
        while (resources.size() < numberOfValues) {
            try {
                getCondition.await();
            } catch (InterruptedException e) {
                lock.unlock();
                Thread.currentThread().interrupt();
                return;
            }
        }

        resources.subList(0, numberOfValues).clear();
        putCondition.signal();
        lock.unlock();
    }
}
