package lab04.version2;

import lab04.IBuffer;

public class Consumer extends Thread {
    private final IBuffer buffer;
    private final int numberOfOperations;
    private final int numberOfElements;

    public Consumer(IBuffer buffer, int numberOfOperations, int numberOfElements) {
        this.buffer = buffer;
        this.numberOfOperations = numberOfOperations;
        this.numberOfElements = numberOfElements;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfOperations; i++)
            buffer.get(numberOfElements);
    }
}
