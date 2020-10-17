package lab2.zad2;

public class Race {
    public static void main(String[] args) throws InterruptedException {
        Counter cnt = new Counter(0);
        Semaphore semaphore = new Semaphore();

        DThread dThread = new DThread(cnt, semaphore);
        IThread iThread = new IThread(cnt, semaphore);

        dThread.start();
        iThread.start();

        dThread.join();
        iThread.join();

        System.out.println("stan=" + cnt.value());
    }
}
