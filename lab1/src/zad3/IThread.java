package zad3;

public class IThread extends Thread {
    private final Counter counter;

    public IThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            counter.changeReadyStatus(1, true);
            counter.setCanGo(0);
            while (counter.getReadyToGo(0) && counter.getCanGo() == 0) {
                // wait for your turn
            }

            counter.inc();
            counter.changeReadyStatus(1, false);
        }
    }
}