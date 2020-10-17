package lab2.zad2;

class Semaphore {
    private boolean isFree = true;
    private int waiting = 0;

    public Semaphore() {
    }

    public synchronized void P() throws InterruptedException {
        waiting++;
        if (!isFree) {
            wait(); // watek zostaje obudzony i idzie na kolejke procesow gotowych i tam czeka na wejscie do monitora (wyscig)
        } // inny watek ktory konczy P wygrywa wyscig i dlatego isFree == false, ale mimo to pierwszy watek dostaje dostep
        // isFree == false
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
