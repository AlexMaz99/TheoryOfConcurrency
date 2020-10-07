package zad3;

import java.util.concurrent.Semaphore;

public class Counter {
    private int _val;
    private volatile boolean[] readyToGo = {false, false};
    private volatile int canGo = 0;
//    private Semaphore semaphore = new Semaphore(1, true);

    public Counter(int n) {
        _val = n;

    }

    public void inc() {
//        semaphore.acquire();
        _val++;
//        semaphore.release();
    }

    public void dec() {
//        semaphore.acquire();
        _val--;
//        semaphore.release();
    }

    public int value() {
        return _val;
    }

    public void changeReadyStatus(int numberOfThread, boolean ready) {
        readyToGo[numberOfThread] = ready;
    }

    public void setCanGo(int numberOfThread) {
        canGo = numberOfThread;
    }

    private boolean cas(boolean old, int i){
        if (old == readyToGo[i])
            return true;
        return false;
    }

    public boolean getReadyToGo(int i) {
        boolean done = false;
        boolean value = false;
        while (!done){
            value = readyToGo[i];
            done = cas(value, i);
        }
        return value;
    }

    public int getCanGo() {
        return canGo;
    }
}