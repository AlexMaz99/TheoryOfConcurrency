package lab04.version1;

import lab04.IBuffer;

import java.util.Random;

public class Producer extends Thread {
    private final String name;
    private int accessToBuffer; // number of accesses to the buffer
    private int producedElements; // number of produced elements
    private final int M;
    private final IBuffer buffer;
    private final Random random = new Random();
    private final Histogram histogram;

    public Producer(IBuffer buffer, int number, Histogram histogram, int M) {
        this.buffer = buffer;
        this.name = "PRODUCER-" + number;
        this.histogram = histogram;
        this.accessToBuffer = 0;
        this.producedElements = 0;
        this.M = M;
    }


    @Override
    public void run() {
        int[] elements;
        while (true) {
            int numberOfElements = random.nextInt(M) + 1;
            producedElements += numberOfElements;
            elements = new int[numberOfElements];
            for (int i = 0; i < numberOfElements; i++) {
                elements[i] = i;
            }
            buffer.put(elements);
            if (Thread.currentThread().isInterrupted()) {
                histogram.addElements(name, producedElements);
                histogram.addAccess(name, accessToBuffer);
                return;
            }
            accessToBuffer++;
        }
    }
}
