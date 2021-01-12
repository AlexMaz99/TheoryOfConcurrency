package lab04.version1;

import lab04.IBuffer;

import java.util.Random;

public class Consumer extends Thread {
    private final String name;
    private int accessToBuffer; // number of accesses to the buffer
    private int consumedElements; // number of consumed elements
    private final int M;
    private final IBuffer buffer;
    private final Random random = new Random();
    private final Histogram histogram;

    public Consumer(IBuffer buffer, int number, Histogram histogram, int M) {
        this.buffer = buffer;
        this.name = "CONSUMER-" + number;
        this.histogram = histogram;
        this.accessToBuffer = 0;
        this.M = M;
    }


    @Override
    public void run() {
        while (true) {
            int numberOfElements = random.nextInt(M) + 1;
            consumedElements += numberOfElements;
            buffer.get(numberOfElements);
            if (Thread.currentThread().isInterrupted()) {
                histogram.addElements(name, consumedElements);
                histogram.addAccess(name, accessToBuffer);
                return;
            }
            accessToBuffer++;
        }
    }
}

