package lab2.zad3;

import java.util.concurrent.Semaphore;

public class CountingSemaphore {
    private BinarySemaphore S1; // 1 - free
    private BinarySemaphore S2; // 0 - not
    private int val;

    public CountingSemaphore(int val) {
        this.S1 = new BinarySemaphore(0, true);
        this.S2 = new BinarySemaphore(0, false);
        this.val = val;
    }

    // acquire
    public synchronized void P() throws InterruptedException {
        S1.P();
        val -= 1;
        if (val <= 0){
            S2.P();
            S1.V();
        }
        else S1.V();
    }

    public synchronized void V() throws InterruptedException {
        S1.P();
        val += 1;
        if (val > 0){
            S2.V();
        }
        S1.V();
    }

    public int getVal() {
        return val;
    }
}
