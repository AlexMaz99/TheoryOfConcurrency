package lab1.zad3;

class DThread extends Thread {
    private final Counter counter;

    public DThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            counter.changeReadyStatus(0, true);
            counter.setThreadWithAccess(1);
            while (counter.isThreadReady(1) && counter.hasAccess(1)) {
                // do nothing
            }

            counter.dec();
            counter.changeReadyStatus(0, false);
        }
    }
}
