package lab3.zad1;

import java.util.Random;

public class Consumer extends Thread {
    private final Buffer buffer;
    private final Random random = new Random();

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        for (int i = 0; i < 100; ++i) {
            try {
                this.buffer.get();
                sleep( random.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
