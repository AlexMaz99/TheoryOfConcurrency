package lab5.zad1;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Library {
    public static void main(String[] args) throws InterruptedException {
        int writers = 10;
        int readers = 20;
        SemaphoresBook semaphoresBook = new SemaphoresBook();

        ExecutorService executorService = Executors.newFixedThreadPool(writers + readers);
        for (int i = 0; i < writers; i++)
            executorService.submit(new Writer(semaphoresBook));
        for (int i = 0; i < readers; i++)
            executorService.submit(new Reader(semaphoresBook));
        Thread.sleep(1000);
        executorService.shutdown();
    }
}
