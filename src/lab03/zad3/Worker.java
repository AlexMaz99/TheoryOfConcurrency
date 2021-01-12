package lab03.zad3;

import java.util.Random;

public class Worker extends Thread {
    private final Buffer buffer;
    private final int number;
    private final Random random = new Random();

    public Worker(Buffer buffer, int number) {
        this.buffer = buffer;
        this.number = number;
    }

    public void run() {
        for (int i = 0; i < 100; ++i) {
            try {
                this.buffer.work(number);
                sleep(random.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
