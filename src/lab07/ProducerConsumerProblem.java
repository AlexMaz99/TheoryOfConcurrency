package lab7;

import lab7.proxy.Proxy;

import java.util.ArrayList;
import java.util.List;

public class ProducerConsumerProblem {

    public static void main(String[] args) throws InterruptedException {
        runTest(10, 10, 20);
    }

    private static void runTest(int producers, int consumers, int bufferSize) throws InterruptedException {
        ActiveObject activeObject = new ActiveObject(bufferSize);
        Proxy proxy = activeObject.getProxy();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < producers; i++)
            threads.add(new Producer(proxy, i));
        for (int i = 0; i < consumers; i++)
            threads.add(new Consumer(proxy, i));

        threads.forEach(Thread::start);
        for (Thread thread : threads)
            thread.join();
    }
}
