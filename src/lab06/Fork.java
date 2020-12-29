package lab6;

public class Fork {
    private final int number;
    private volatile boolean state = true;

    public Fork(int number) {
        this.number = number;
    }

    public synchronized void acquire() {
        try {
            while (!state)
                wait();
            state = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void release() {
        state = true;
        notify();
    }

    public int getNumber() {
        return this.number;
    }

    public boolean isReleased() {
        return state;
    }
}

