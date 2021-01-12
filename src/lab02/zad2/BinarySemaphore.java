package lab02.zad2;

class BinarySemaphore {
    private boolean status = true;

    public BinarySemaphore() {
    }

    public synchronized void P() throws InterruptedException {
        if (!status) {
            this.wait();
        }
        status = false;
    }

    public synchronized void V() {
        status = true;
        this.notify();
    }
}
