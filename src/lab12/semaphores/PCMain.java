package lab12.semaphores;

public class PCMain {
    static final int N_BUFFERS = 200;
    static final int N_ITEMS = 10000;

    public static void main(String[] args) throws InterruptedException {
        Buffer buffer = new Buffer(N_BUFFERS);
        Producer producer = new Producer(buffer, N_ITEMS);
        Consumer consumer = new Consumer(buffer, N_ITEMS);

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();
    }
}
