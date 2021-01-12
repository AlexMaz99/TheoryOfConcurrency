package lab01.zad3;

class IThread extends Thread {
    private final Counter counter;

    public IThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            counter.changeReadyStatus(1, true);
            counter.setThreadWithAccess(0);
            while (counter.isThreadReady(0) && counter.hasAccess(0)) {
                // do nothing
            }

            counter.inc();
            counter.changeReadyStatus(1, false);
        }
    }
}
