package lab6;

import java.util.concurrent.Semaphore;

public class PhilosopherWithArbiter extends Philosopher {
    private final Semaphore atTheTable;

    public PhilosopherWithArbiter(Fork leftFork, Fork rightFork, Semaphore atTheTable) {
        super(leftFork, rightFork);
        this.atTheTable = atTheTable;
    }

    @Override
    void startEating() {
        try {
            atTheTable.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        leftFork.acquire();
        rightFork.acquire();
    }

    @Override
    void endEating() {
        leftFork.release();
        rightFork.release();
        atTheTable.release();
    }
}
