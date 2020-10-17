package lab2.zad3;

class IThread extends Thread {
    private final Counter counter;
    private final CountingSemaphore semaphore;

    public IThread(Counter counter, CountingSemaphore semaphore) {
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
                try {
                    semaphore.V();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
