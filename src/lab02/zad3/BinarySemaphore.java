package lab02.zad3;

class BinarySemaphore {
    private volatile boolean status;

    public BinarySemaphore(boolean status) {
        this.status = status;
    }

    public synchronized void P() throws InterruptedException {
        while (!status) {
            this.wait();
        }
        status = false;
    }

    public synchronized void V() {
        status = true;
        this.notify();
    }
}
