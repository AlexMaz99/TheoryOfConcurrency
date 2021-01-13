package lab12.semaphores;

public class Producer extends Thread {
    private final Buffer buffer;
    private final int nItems;

    public Producer(Buffer buffer, int nItems) {
        this.buffer = buffer;
        this.nItems = nItems;
    }

    public void run() {
        for (int i = 0; i < nItems; i++) {
            try {
                var item = (int) (Math.random() * 100) + 1;
                this.buffer.put(item);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
