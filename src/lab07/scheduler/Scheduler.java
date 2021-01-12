package lab07.scheduler;

import lab07.requests.MethodRequest;

public class Scheduler extends Thread {
    private final ActivationQueue activationQueue = new ActivationQueue();

    public void enqueue(MethodRequest methodRequest) {
        activationQueue.enqueue(methodRequest);
    }

    private void dispatch() {
        if (activationQueue.isEmpty())
            return;
        MethodRequest methodRequest = activationQueue.dequeue();
        if (methodRequest.guard())
            methodRequest.call();
        else
            enqueue(methodRequest);
    }

    @Override
    public void run() {
        while (true) {
            dispatch();
        }
    }
}
