package lab1.zad3;

class Counter {
    private int _val;

    // whether threads are ready to enter the critical section
    private volatile boolean[] isReady = {false, false};

    // thread that has access to the critical section
    // 0 - DThread has access, 1 - IThread has access
    private volatile int threadWithAccess = 0;

    public Counter(int n) {
        _val = n;
    }

    public void inc() {
        _val++;
    }

    public void dec() {
        _val--;
    }

    public int value() {
        return _val;
    }

    public void changeReadyStatus(int numberOfThread, boolean ready) {
        isReady[numberOfThread] = ready;
    }

    public void setThreadWithAccess(int numberOfThread) {
        threadWithAccess = numberOfThread;
    }

    public boolean isThreadReady(int numberOfThread) {
        return isReady[numberOfThread];
    }

    public boolean hasAccess(int numberOfThread) {
        return threadWithAccess == numberOfThread;
    }
}
