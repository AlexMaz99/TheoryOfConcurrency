package lab2.zad1;

class Semaphore {
    private boolean isFree = true;
    private int waiting = 0;

    public Semaphore() {
    }

    public synchronized void P() throws InterruptedException {
        waiting++;
        while (!isFree) {
            wait();
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

