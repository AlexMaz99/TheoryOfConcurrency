package lab03.zad3;

import java.util.Random;

public class Producer extends Thread {
    private final Buffer buffer;
    private final Random random = new Random();

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        for (int i = 0; i < 100; ++i) {
            try {
                this.buffer.put(i);
                sleep(random.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
