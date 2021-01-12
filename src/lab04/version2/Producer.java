package lab04.version2;

import lab04.IBuffer;

public class Producer extends Thread {
    private final IBuffer buffer;
    private final int numberOfOperations;
    private final int numberOfElements;

    public Producer(IBuffer buffer, int numberOfOperations, int numberOfElements) {
        this.buffer = buffer;
        this.numberOfOperations = numberOfOperations;
        this.numberOfElements = numberOfElements;
    }

    @Override
    public void run() {
        int[] elements = new int[numberOfElements];

        for (int i = 0; i < numberOfElements; i++)
            elements[i] = i;

        for (int i = 0; i < numberOfOperations; i++)
            buffer.put(elements);

    }
}
