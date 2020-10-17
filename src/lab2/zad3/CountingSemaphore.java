package lab2.zad3;

class CountingSemaphore {
    private final BinarySemaphore valSemaphore; // is used to protect the variable val
    private final BinarySemaphore threadSemaphore; // is used to suspend processes / threads
    private volatile int val;

    public CountingSemaphore(int val) {
        this.valSemaphore = new BinarySemaphore(true);
        this.threadSemaphore = new BinarySemaphore(false);
        this.val = val;
    }

    public void P() throws InterruptedException {
        valSemaphore.P();
        val -= 1;
        if (val < 0) {
            valSemaphore.V();
            threadSemaphore.P();
        }
        valSemaphore.V();
    }

    public void V() throws InterruptedException {
        valSemaphore.P();
        val += 1;
        if (val <= 0) {
            threadSemaphore.V();
        }
        else valSemaphore.V();
    }

    public int getVal() {
        return val;
    }
}
