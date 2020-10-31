package lab4.version2;

import lab4.IBuffer;
import lab4.JavaBuffer;
import lab4.JavaConcurrencyBuffer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProducersConsumersProblem {
    public static void main(String[] args) throws InterruptedException {
        test1(10);
        test2(20);
        test3();
    }

    private static double measureTime(ExecutorService executorService) {
        long startTime = System.nanoTime();
        executorService.shutdownNow();
        return (double) (System.nanoTime() - startTime) / 1000000;
    }

    private static void executeOperations(int M, int numberOfProducers, int numberOfOperations) throws InterruptedException {
        IBuffer javaConcurrencyBuffer = new JavaConcurrencyBuffer(M);
        IBuffer javaBuffer = new JavaBuffer(M);

        int portionStep = M / numberOfProducers;
        int numberOfElements = M;

        ExecutorService executorServiceForJCUBuffer = Executors.newFixedThreadPool(2 * numberOfProducers);
        ExecutorService executorServiceForJBuffer = Executors.newFixedThreadPool(2 * numberOfProducers);

        for (int i = 0; i < numberOfProducers; i++) {
            executorServiceForJCUBuffer.submit(new Producer(javaConcurrencyBuffer, numberOfOperations, numberOfElements));
            executorServiceForJBuffer.submit(new Producer(javaBuffer, numberOfOperations, numberOfElements));

            executorServiceForJCUBuffer.submit(new Consumer(javaConcurrencyBuffer, numberOfOperations, numberOfElements));
            executorServiceForJBuffer.submit(new Consumer(javaBuffer, numberOfOperations, numberOfElements));
            numberOfElements -= portionStep;
        }

        Thread.sleep(1000);

        double javaConcurrencyTime = measureTime(executorServiceForJCUBuffer);
        double javaTime = measureTime(executorServiceForJBuffer);
        System.out.println("Size of buffer = " + 2 * M +
                ", Number of producers = Number of consumers = " + numberOfProducers +
                ", Java Concurrency Utilities Time = " + javaConcurrencyTime + " ms" +
                ", Java Time = " + javaTime + " ms");
    }

    // constants: number of producers, number of consumers, variables: size of buffer
    // additional condition: number of producers = number of consumers
    private static void test1(int numberOfProducers) throws InterruptedException {
        System.out.println("TEST 1\n");
        int numberOfOperations = 100;
        for (int M = 20; M <= 240; M += 20) {
            executeOperations(M, numberOfProducers, numberOfOperations);
        }
    }

    // constants: size of buffer, variables: number of producers, number of consumers
    // additional condition: number of producers = number of consumers
    private static void test2(int M) throws InterruptedException {
        System.out.println("TEST 2\n");
        int numberOfOperations = 100;
        for (int numberOfProducers = 1; numberOfProducers <= 24; numberOfProducers += 2) {
            executeOperations(M, numberOfProducers, numberOfOperations);
        }
    }

    // variables: number of producers, number of consumers, size of buffer
    // additional condition: number of producers = number of consumers
    private static void test3() throws InterruptedException {
        System.out.println("TEST 3\n");
        int numberOfOperations = 50;
        for (int M = 20; M <= 140; M += 20) {
            for (int numberOfProducers = 1; numberOfProducers <= 11; numberOfProducers += 2) {
                executeOperations(M, numberOfProducers, numberOfOperations);
            }
            System.out.println("\n");
        }
    }
}

