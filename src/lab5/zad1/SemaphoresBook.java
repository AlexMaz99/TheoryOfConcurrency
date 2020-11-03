package lab5.zad1;

import java.util.concurrent.Semaphore;

public class SemaphoresBook implements IBook {
    private volatile int numberOfReaders = 0;
    private final Semaphore readersUpdateSemaphore = new Semaphore(1);
    private final Semaphore writersSemaphore = new Semaphore(1);

    @Override
    public void read() throws InterruptedException {
        readersUpdateSemaphore.acquire();
        numberOfReaders++;
        if (numberOfReaders == 1)
            writersSemaphore.acquire();
        readersUpdateSemaphore.release();
        System.out.println("I'm reading");
        readersUpdateSemaphore.acquire();
        numberOfReaders--;
        if (numberOfReaders == 0)
            writersSemaphore.release();
        readersUpdateSemaphore.release();
    }

    @Override
    public void write() throws InterruptedException {
        writersSemaphore.acquire();
        System.out.println("I'm writing");
        writersSemaphore.release();
    }
}
