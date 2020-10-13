package lab2.zad3;

public class BinarySemaphore {
    private volatile boolean isFree = true;
    private volatile int waiting = 0;

    public BinarySemaphore(int waiting, boolean isFree) {
        this.waiting = waiting;
        this.isFree = isFree;
    }

    public synchronized void P() throws InterruptedException {
        waiting++;
        while (!isFree) {
            this.wait();
        }
        waiting--;
        isFree = false;
    }

    public synchronized void V() {
        isFree = true;
        if (waiting > 0) {
            this.notify();
        }
    }
}
