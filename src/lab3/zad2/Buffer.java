package lab3.zad2;

import java.util.concurrent.Semaphore;

public class Buffer {
    private final int[] resources;
    private int putIndex = 0, getIndex = 0;
    private final int resourceCapacity;
    private final Semaphore producedResourcesSemaphore = new Semaphore(0);
    private final Semaphore freeResourcesSemaphore;
    private final Semaphore accessSemaphore = new Semaphore(1);

    public Buffer(int resourceCapacity) {
        this.resourceCapacity = resourceCapacity;
        this.freeResourcesSemaphore = new Semaphore(resourceCapacity);
        this.resources = new int[resourceCapacity];
    }

    public void put(int i) throws InterruptedException {
        this.freeResourcesSemaphore.acquire();
        this.accessSemaphore.acquire();

        System.out.println("Producer puts " + i);
        this.resources[this.putIndex++] = i;
        this.putIndex %= this.resourceCapacity;

        this.accessSemaphore.release();
        this.producedResourcesSemaphore.release();
    }

    public int get() throws InterruptedException {
        this.producedResourcesSemaphore.acquire();
        this.accessSemaphore.acquire();

        int result = this.resources[this.getIndex];
        this.resources[this.getIndex++] = -1;
        this.getIndex %= this.resourceCapacity;
        System.out.println("Consumer gets " + result);

        this.accessSemaphore.release();
        this.freeResourcesSemaphore.release();

        return result;
    }
}
