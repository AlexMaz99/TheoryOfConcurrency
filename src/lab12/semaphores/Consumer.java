package lab12.semaphores;


public class Consumer extends Thread {
    private final Buffer buffer;
    private final int nItems;

    public Consumer(Buffer buffer, int nItems) {
        this.buffer = buffer;
        this.nItems = nItems;
    }

    public void run() {
        var startTime = System.nanoTime();

        for (int i = 0; i < nItems; i++) {
            try {
                this.buffer.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("TIME: " + (System.nanoTime() - startTime) / 1000000 + "ms");
        System.exit(0);
    }
}