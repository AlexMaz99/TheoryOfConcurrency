package lab03.zad2;

public class Consumer extends Thread {
    private final Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        for (int i = 0; i < 100; ++i) {
            try {
                this.buffer.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
