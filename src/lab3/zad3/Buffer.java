package lab3.zad3;

import java.util.concurrent.Semaphore;

public class Buffer {
    private int producerIndex = 0;
    private int consumerIndex = 0;
    private final int resourceCapacity, numberOfWorkers;
    private final int[] resources;
    private final int[] workersIndexes;
    private final Semaphore accessSemaphore;
    private final Semaphore producedResourcesSemaphore;
    private final Semaphore freeResourcesSemaphore;
    private final Semaphore[] workersSemaphores;

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
        this.freeResourcesSemaphore.acquire();
        this.accessSemaphore.acquire();

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
        this.producedResourcesSemaphore.acquire();
        this.accessSemaphore.acquire();

        int result = this.resources[this.consumerIndex];
        this.resources[this.consumerIndex++] = -1;
        this.consumerIndex %= this.resourceCapacity;
        System.out.println("Consumer gets " + result);

        this.accessSemaphore.release();
        this.freeResourcesSemaphore.release();

        return result;
    }

    public void work(int workerNumber) throws InterruptedException {
        this.workersSemaphores[workerNumber].acquire();
        this.accessSemaphore.acquire();

        System.out.println("Worker " + workerNumber + " delivers " + this.resources[this.workersIndexes[workerNumber]++]);
        this.workersIndexes[workerNumber] %= this.resourceCapacity;

        this.accessSemaphore.release();
        if (workerNumber < this.numberOfWorkers - 1)
            this.workersSemaphores[workerNumber + 1].release();
        else
            this.producedResourcesSemaphore.release();
    }
}
