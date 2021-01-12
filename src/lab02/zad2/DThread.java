package lab02.zad2;


class DThread extends Thread {
    private final Counter counter;
    private final BinarySemaphore binarySemaphore;

    public DThread(Counter counter, BinarySemaphore binarySemaphore) {
        this.counter = counter;
        this.binarySemaphore = binarySemaphore;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            try {
                binarySemaphore.P();
                counter.dec();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                binarySemaphore.V();
            }
        }
    }
}
