package lab2.zad3;

public class BinarySemaphore {
    private volatile int status;

    public BinarySemaphore(int status) {
        this.status = status;
    }

    public synchronized void P() throws InterruptedException {
        while (status <= 0) {
            this.wait();
        }
        status--;
    }

    public synchronized void V() {
        status++;
        this.notify();
    }
}
