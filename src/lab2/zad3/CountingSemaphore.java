package lab2.zad3;

import java.util.concurrent.Semaphore;

public class CountingSemaphore {
    private BinarySemaphore S1; // 1 - free
    private BinarySemaphore S2; // 0 - not
    private volatile int val;

    public CountingSemaphore(int val) {
        this.S1 = new BinarySemaphore(1);
        this.S2 = new BinarySemaphore(0);
        this.val = val;
    }

    // acquire
    public void P() throws InterruptedException {
        S1.P();
        val -= 1;
        if (val < 0){
            S1.V();
            S2.P();
        }
        else S1.V();
    }

    public void V() throws InterruptedException {
        S1.P();
        val += 1;
        if (val <= 0){
            S2.V();
        }
        S1.V();
    }

    public int getVal() {
        return val;
    }
}
