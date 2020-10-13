package lab1.zad2;

class IThread extends Thread {
    private final Counter counter;

    public IThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            counter.inc();
        }
    }
}
