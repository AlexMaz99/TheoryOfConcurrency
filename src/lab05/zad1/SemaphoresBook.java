package lab05.zad1;

import java.util.concurrent.Semaphore;

public class SemaphoresBook implements IBook {
    private volatile int activeReaders = 0;
    private volatile int activeWriters = 0;
    private final Semaphore ARSemaphore = new Semaphore(1); // semaphore which guards access to variable "activeReaders"
    private final Semaphore AWSemaphore = new Semaphore(1); // semaphore which guards access to variable "activeWriters"
    private final Semaphore writersSemaphore = new Semaphore(1); // writers wait on this semaphore
    private final Semaphore readerSemaphore = new Semaphore(1); // only one reader can wait on this semaphore
    private final Semaphore readersSemaphore = new Semaphore(1); // rest of the readers wait on this semaphore

    @Override
    public void read() throws InterruptedException {
        readersSemaphore.acquire();
        readerSemaphore.acquire();
        ARSemaphore.acquire();
        activeReaders++;
        if (activeReaders == 1)
            writersSemaphore.acquire();
        ARSemaphore.release();
        readerSemaphore.release();
        readersSemaphore.release();

        // READING

        ARSemaphore.acquire();
        activeReaders--;
        if (activeReaders == 0)
            writersSemaphore.release();
        ARSemaphore.release();

    }

    @Override
    public void write() throws InterruptedException {
        AWSemaphore.acquire();
        activeWriters++;
        if (activeWriters == 1)
            readerSemaphore.acquire();
        AWSemaphore.release();
        writersSemaphore.acquire();

        // WRITING

        writersSemaphore.release();
        AWSemaphore.acquire();
        activeWriters--;
        if (activeWriters == 0)
            readerSemaphore.release();
        AWSemaphore.release();

    }
}
