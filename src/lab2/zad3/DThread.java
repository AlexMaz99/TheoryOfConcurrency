package lab2.zad3;


class DThread extends Thread {
    private final Counter counter;
    private final CountingSemaphore semaphore;

    public DThread(Counter counter, CountingSemaphore semaphore) {
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
                try {
                    semaphore.V();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
