package lab03.zad2;

import java.util.ArrayList;
import java.util.List;

public class PKmon {
    public static void main(String[] args) throws InterruptedException {
        Buffer buffer = new Buffer(50);
        List<Producer> producers = new ArrayList<>();
        List<Consumer> consumers = new ArrayList<>();

        int n1 = 5; // number of producers
        int n2 = 5; // number of consumers

        for (int i = 0; i < n1; i++)
            producers.add(new Producer(buffer));

        for (int i = 0; i < n2; i++)
            consumers.add(new Consumer(buffer));

        producers.forEach(Producer::start);
        consumers.forEach(Consumer::start);

        for (Producer producer : producers)
            producer.join();
        for (Consumer consumer : consumers)
            consumer.join();
    }
}
