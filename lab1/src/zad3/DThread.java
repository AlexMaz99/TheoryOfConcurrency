package zad3;

public class DThread extends Thread {
    private final Counter counter;

    public DThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            counter.changeReadyStatus(0, true);
            counter.setCanGo(1);
            while (counter.getReadyToGo(1) && counter.getCanGo() == 1) {
                // wait for your turn
            }

            counter.dec();
            counter.changeReadyStatus(0, false);
        }
    }
}