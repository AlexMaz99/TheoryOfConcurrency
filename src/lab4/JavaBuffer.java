package lab4;

import java.util.ArrayList;
import java.util.List;

// java buffer
public class JavaBuffer implements IBuffer {
    private final int M; // 2 * M - size of buffer
    private final List<Integer> resources = new ArrayList<>();

    public JavaBuffer(int M) {
        this.M = M;
    }

    @Override
    public synchronized void put(int[] values) {
        while (2 * M - resources.size() < values.length) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        for (Integer value : values)
            resources.add(value);
        notifyAll();
    }

    @Override
    public synchronized void get(int numberOfValues) {
        while (resources.size() < numberOfValues) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        resources.subList(0, numberOfValues).clear();
        notifyAll();
    }
}
