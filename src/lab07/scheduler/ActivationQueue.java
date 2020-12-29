package lab7.scheduler;

import lab7.requests.MethodRequest;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ActivationQueue {
    private final Queue<MethodRequest> queue = new ConcurrentLinkedDeque<>();

    public void enqueue(MethodRequest methodRequest) {
        queue.add(methodRequest);
    }

    public MethodRequest dequeue() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
