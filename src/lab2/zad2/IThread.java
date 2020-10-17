package lab2.zad2;


class IThread extends Thread {
    private final Counter counter;
    private final BinarySemaphore binarySemaphore;

    public IThread(Counter counter, BinarySemaphore binarySemaphore) {
        this.counter = counter;
        this.binarySemaphore = binarySemaphore;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            try {
                binarySemaphore.P();
                counter.inc();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                binarySemaphore.V();
            }
        }
    }
}
