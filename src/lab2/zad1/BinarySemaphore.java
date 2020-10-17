package lab2.zad1;

class BinarySemaphore {
    private boolean status = true;

    public BinarySemaphore() {
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


