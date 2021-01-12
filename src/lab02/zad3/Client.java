package lab02.zad3;

class Client extends Thread {
    private final CountingSemaphore countingSemaphore;
    private final Counter counter;

    public Client(CountingSemaphore countingSemaphore, Counter counter) {
        this.countingSemaphore = countingSemaphore;
        this.counter = counter;
    }

    @Override
    public void run() {
        try {
            countingSemaphore.P();
            counter.inc();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            countingSemaphore.V();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
