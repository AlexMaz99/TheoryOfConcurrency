package lab6;

public class PhilosopherWithStarve extends Philosopher {

    public PhilosopherWithStarve(Fork leftFork, Fork rightFork) {
        super(leftFork, rightFork);
    }

    @Override
    void startEating() {
        while (!(leftFork.isReleased() && rightFork.isReleased())) {
            // do nothing
        }
        leftFork.acquire();
        rightFork.acquire();
    }

    @Override
    void endEating() {
        leftFork.release();
        rightFork.release();
    }

}
