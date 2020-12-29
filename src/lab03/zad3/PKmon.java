package lab3.zad3;

import java.util.ArrayList;
import java.util.List;

public class PKmon {
    public static void main(String[] args) throws InterruptedException {
        int N = 5;
        int capacity = 100;

        Buffer buffer = new Buffer(capacity, N);
        List<Thread> threadList = new ArrayList<>();

        threadList.add(new Producer(buffer));

        for (int i = 0; i < N; i++)
            threadList.add(new Worker(buffer, i));

        threadList.add(new Consumer(buffer));

        threadList.forEach(Thread::start);

        for (Thread thread : threadList)
            thread.join();
    }
}
