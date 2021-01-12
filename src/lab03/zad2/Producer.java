package lab03.zad2;

public class Producer extends Thread {
    private final Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        for (int i = 0; i < 100; ++i) {
            try {
                this.buffer.put(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
