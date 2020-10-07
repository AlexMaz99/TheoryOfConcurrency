package zad2;

public class DThread extends Thread {
    private final Counter counter;

    public DThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            counter.dec();
        }
    }
}
