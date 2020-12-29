package lab2.zad3;

class Race {
    public static void main(String[] args) throws InterruptedException {
        Counter cnt = new Counter(0);
        CountingSemaphore semaphore = new CountingSemaphore(1);

        DThread dThread = new DThread(cnt, semaphore);
        IThread iThread = new IThread(cnt, semaphore);

        dThread.start();
        iThread.start();

        dThread.join();
        iThread.join();

        System.out.println("stan=" + cnt.value());
    }
}

