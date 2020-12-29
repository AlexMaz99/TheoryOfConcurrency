package lab2.zad2;

class Race {
    public static void main(String[] args) throws InterruptedException {
        Counter cnt = new Counter(0);
        BinarySemaphore binarySemaphore = new BinarySemaphore();

        DThread dThread = new DThread(cnt, binarySemaphore);
        IThread iThread = new IThread(cnt, binarySemaphore);

        dThread.start();
        iThread.start();

        dThread.join();
        iThread.join();

        System.out.println("stan=" + cnt.value());
    }
}
