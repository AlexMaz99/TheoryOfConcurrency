package lab06;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class DiningPhilosophersProblem {
    private static final int N = 5; // number of philosophers

    public static void main(String[] args) throws InterruptedException {
        runPhilosophersWithBlockade();
        runPhilosophersWithStarve();
        runPhilosophersWithArbiter();
    }

    private static void runPhilosophersWithBlockade() throws InterruptedException {
        List<Philosopher> philosophers = createPhilosophersWithBlockade();
        runPhilosophers(philosophers);
    }

    private static void runPhilosophersWithStarve() throws InterruptedException {
        List<Philosopher> philosophers = createPhilosophersWithStarve();
        runPhilosophers(philosophers);
    }

    private static void runPhilosophersWithArbiter() throws InterruptedException {
        List<Philosopher> philosophers = createPhilosophersWithArbiter();
        runPhilosophers(philosophers);
    }

    private static List<Fork> createForks() {
        List<Fork> forks = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            forks.add(new Fork(i));
        }
        return forks;
    }

    private static List<Philosopher> createPhilosophersWithBlockade() {
        List<Philosopher> philosophers = new ArrayList<>();
        List<Fork> forks = createForks();
        for (int i = 0; i < N; i++) {
            Philosopher philosopher = new PhilosopherWithBlockade(forks.get(i), forks.get((i + 1) % N));
            philosophers.add(philosopher);
        }
        return philosophers;
    }

    private static List<Philosopher> createPhilosophersWithStarve() {
        List<Philosopher> philosophers = new ArrayList<>();
        List<Fork> forks = createForks();
        for (int i = 0; i < N; i++) {
            Philosopher philosopher = new PhilosopherWithStarve(forks.get(i), forks.get((i + 1) % N));
            philosophers.add(philosopher);
        }
        return philosophers;
    }

    private static List<Philosopher> createPhilosophersWithArbiter() {
        List<Philosopher> philosophers = new ArrayList<>();
        List<Fork> forks = createForks();
        Semaphore atTheTable = new Semaphore(N - 1);
        for (int i = 0; i < N; i++) {
            Philosopher philosopher = new PhilosopherWithArbiter(forks.get(i), forks.get((i + 1) % N), atTheTable);
            philosophers.add(philosopher);
        }
        return philosophers;
    }

    private static void runPhilosophers(List<Philosopher> philosophers) throws InterruptedException {
        long startTime = System.nanoTime();
        philosophers.forEach(Philosopher::start);
        for (Philosopher philosopher : philosophers)
            philosopher.join();
        System.out.println("\nTime: " + (System.nanoTime() - startTime) / 1000000 + " ms");
    }
}
