package lab2.zad1;


class DThread extends Thread {
    private final Counter counter;
    private final Semaphore semaphore;

    public DThread(Counter counter, Semaphore semaphore) {
        this.counter = counter;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            try {
                semaphore.P();
                counter.dec();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.V();
            }
        }
    }
}
