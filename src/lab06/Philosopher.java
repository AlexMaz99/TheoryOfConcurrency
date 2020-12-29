package lab6;

import java.util.Random;

public abstract class Philosopher extends Thread {
    private int counter = 0;
    final Fork leftFork;
    final Fork rightFork;
    final Random random = new Random();

    public Philosopher(Fork leftFork, Fork rightFork) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    abstract void startEating();

    abstract void endEating();

    @Override
    public void run() {
        while (true) {
            think();
            eat();
            if (Thread.currentThread().isInterrupted())
                return;
        }
    }

    private void think() {
        try {
            sleep(random.nextInt(50));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void eat() {
        startEating();
        try {
            sleep(random.nextInt(50));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ++counter;
        if (counter % 100 == 0) {
            System.out.println("Philosopher: " + Thread.currentThread() +
                    " has eaten " + counter + " times");
            if (counter == 500) {
                Thread.currentThread().interrupt();
            }
        }
        endEating();
    }
}
