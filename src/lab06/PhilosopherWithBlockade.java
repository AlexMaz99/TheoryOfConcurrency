package lab6;

public class PhilosopherWithBlockade extends Philosopher {

    public PhilosopherWithBlockade(Fork leftFork, Fork rightFork) {
        super(leftFork, rightFork);
    }

    @Override
    void startEating() {
        pickUpFork(leftFork);
        try {
            sleep(random.nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pickUpFork(rightFork);
    }

    private void pickUpFork(Fork fork) {
        System.out.println("Philosopher: " + Thread.currentThread() +
                " wants to pick up " + (fork.equals(leftFork) ? "left" : "right") + " fork nr " + fork.getNumber());
        fork.acquire();
        System.out.println("Philosopher: " + Thread.currentThread() +
                " picked up " + (fork.equals(leftFork) ? "left" : "right") + " fork nr " + fork.getNumber());
    }

    @Override
    void endEating() {
        leftFork.release();
        rightFork.release();
    }
}
