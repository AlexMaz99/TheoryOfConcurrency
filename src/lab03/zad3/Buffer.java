package lab03.zad3;

import java.util.concurrent.Semaphore;

public class Buffer {
    private int producerIndex = 0; // current index of table "resources" for producer
    private int consumerIndex = 0; // current index of table "resources" for consumer
    private final int resourceCapacity, numberOfWorkers; // number of resources and number of workers
    private final int[] resources; // table of resources
    private final int[] workersIndexes; // current indexes of table "resources" for every worker

    private final Semaphore accessSemaphore; // access to resources
    private final Semaphore producedResourcesSemaphore; // number of produced resources which are ready to take by consumer
    private final Semaphore freeResourcesSemaphore; // number of free places in table "resources"
    private final Semaphore[] workersSemaphores; // supervises the order of workers in accessing resources

    public Buffer(int resourceCapacity, int numberOfWorkers) {
        this.resourceCapacity = resourceCapacity;
        this.numberOfWorkers = numberOfWorkers;
        this.resources = new int[resourceCapacity];
        this.workersIndexes = new int[numberOfWorkers];

        this.accessSemaphore = new Semaphore(1);
        this.producedResourcesSemaphore = new Semaphore(0);
        this.freeResourcesSemaphore = new Semaphore(resourceCapacity);
        this.workersSemaphores = new Semaphore[numberOfWorkers];

        for (int i = 0; i < this.numberOfWorkers; i++) {
            this.workersSemaphores[i] = new Semaphore(0);
            this.workersIndexes[i] = 0;
        }
    }

    public void put(int i) throws InterruptedException {
        this.freeResourcesSemaphore.acquire(); // waits for free place for produced resource
        this.accessSemaphore.acquire(); // waits for access to resources

        System.out.println("Producer puts " + i);
        this.resources[producerIndex++] = i;
        this.producerIndex %= this.resourceCapacity;

        this.accessSemaphore.release();
        if (this.numberOfWorkers > 0)
            this.workersSemaphores[0].release();
        else
            this.producedResourcesSemaphore.release();
    }

    public int get() throws InterruptedException {
        this.producedResourcesSemaphore.acquire(); // waits for resource
        this.accessSemaphore.acquire(); // waits for access to resources

        int result = this.resources[this.consumerIndex];
        this.resources[this.consumerIndex++] = -1;
        this.consumerIndex %= this.resourceCapacity;
        System.out.println("Consumer gets " + result);

        this.accessSemaphore.release();
        this.freeResourcesSemaphore.release();

        return result;
    }

    public void work(int workerNumber) throws InterruptedException {
        this.workersSemaphores[workerNumber].acquire(); // waits for your turn
        this.accessSemaphore.acquire(); // waits for access to resources

        System.out.println("Worker " + workerNumber + " delivers " + this.resources[this.workersIndexes[workerNumber]++]);
        this.workersIndexes[workerNumber] %= this.resourceCapacity;

        this.accessSemaphore.release();
        if (workerNumber < this.numberOfWorkers - 1)
            this.workersSemaphores[workerNumber + 1].release();
        else
            this.producedResourcesSemaphore.release();
    }
}
