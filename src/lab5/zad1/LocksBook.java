package lab5.zad1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LocksBook implements IBook {
    private volatile int activeReaders = 0;
    private volatile int waitingReaders = 0;
    private volatile int activeWriters = 0;
    private volatile int waitingWriters = 0;
    private final Lock lock = new ReentrantLock();
    private final Condition canRead = lock.newCondition();
    private final Condition canWrite = lock.newCondition();

    @Override
    public void read() throws InterruptedException {
        lock.lock();
        while (activeWriters + waitingWriters > 0) {
            waitingReaders++;
            canRead.await();
            waitingReaders--;
        }
        activeReaders++;
        lock.unlock();

        // READING

        lock.lock();
        activeReaders--;
        if (activeReaders == 0 && waitingWriters > 0)
            canWrite.signal();
        lock.unlock();
    }

    @Override
    public void write() throws InterruptedException {
        lock.lock();
        while (activeWriters + activeReaders > 0) {
            waitingWriters++;
            canWrite.await();
            waitingWriters--;
        }
        activeWriters++;
        lock.unlock();

        // WRITING

        lock.lock();
        activeWriters--;
        if (waitingWriters > 0)
            canWrite.signal();
        else if (waitingReaders > 0)
            canRead.signalAll();
        lock.unlock();
    }
}
