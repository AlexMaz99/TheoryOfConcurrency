package lab2.zad1;


class IThread extends Thread {
    private final Counter counter;
    private final Semaphore semaphore;

    public IThread(Counter counter, Semaphore semaphore) {
        this.counter = counter;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            try {
                semaphore.P();
                counter.inc();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.V();
            }
        }
    }
}
