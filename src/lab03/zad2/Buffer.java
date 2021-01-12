package lab03.zad2;

import java.util.concurrent.Semaphore;

public class Buffer {
    private final int[] resources;
    private int putIndex = 0; // current index of table "resources" for producer
    private int getIndex = 0; // current index of table "resources" for consumer
    private final int resourceCapacity; // number of resources
    private final Semaphore producedResourcesSemaphore = new Semaphore(0); // number of produced resources which are ready to take by consumer
    private final Semaphore freeResourcesSemaphore; // number of free places in table "resources"
    private final Semaphore accessSemaphore = new Semaphore(1); // access to resources

    public Buffer(int resourceCapacity) {
        this.resourceCapacity = resourceCapacity;
        this.freeResourcesSemaphore = new Semaphore(resourceCapacity);
        this.resources = new int[resourceCapacity];
    }

    public void put(int i) throws InterruptedException {
        this.freeResourcesSemaphore.acquire(); // waits for free place for produced resource
        this.accessSemaphore.acquire(); // waits for access to resources

        System.out.println("Producer puts " + i);
        this.resources[this.putIndex++] = i;
        this.putIndex %= this.resourceCapacity;

        this.accessSemaphore.release();
        this.producedResourcesSemaphore.release();
    }

    public int get() throws InterruptedException {
        this.producedResourcesSemaphore.acquire(); // waits for resource
        this.accessSemaphore.acquire(); // waits for access to resources

        int result = this.resources[this.getIndex];
        this.resources[this.getIndex++] = -1;
        this.getIndex %= this.resourceCapacity;
        System.out.println("Consumer gets " + result);

        this.accessSemaphore.release();
        this.freeResourcesSemaphore.release();

        return result;
    }
}
