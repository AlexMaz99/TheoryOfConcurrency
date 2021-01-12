package lab04.version1;

import lab04.IBuffer;
import lab04.JavaBuffer;
import lab04.JavaConcurrencyBuffer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProducersConsumersProblem {
    public static void main(String[] args) throws InterruptedException {
        int M = 20; // 2 * M - size of buffer
        int m = 2; // number of producers
        int n = 4; // number of consumers

        IBuffer javaConcurrencyBuffer = new JavaConcurrencyBuffer(M);
        IBuffer javaBuffer = new JavaBuffer(M);
        Histogram histogramJavaConcurrency = new Histogram();
        Histogram histogramJava = new Histogram();

        ExecutorService executorServiceForJCUBuffer = Executors.newFixedThreadPool(m + n);
        ExecutorService executorServiceForJBuffer = Executors.newFixedThreadPool(m + n);

        for (int i = 0; i < m; i++) {
            executorServiceForJCUBuffer.submit(new Producer(javaConcurrencyBuffer, i, histogramJavaConcurrency, M));
            executorServiceForJBuffer.submit(new Producer(javaBuffer, i, histogramJava, M));
        }

        for (int i = 0; i < n; i++) {
            executorServiceForJCUBuffer.submit(new Consumer(javaConcurrencyBuffer, i, histogramJavaConcurrency, M));
            executorServiceForJBuffer.submit(new Consumer(javaBuffer, i, histogramJava, M));
        }

        Thread.sleep(1000);
        executorServiceForJCUBuffer.shutdownNow();
        executorServiceForJCUBuffer.awaitTermination(30, TimeUnit.SECONDS);

        executorServiceForJBuffer.shutdownNow();
        executorServiceForJBuffer.awaitTermination(30, TimeUnit.SECONDS);

        System.out.println("\nSize of buffer: " + M + " Number of producers: " + m + " Number of consumers: " + n + "\n");
        System.out.println("JAVA MONITORS: \n");
        histogramJavaConcurrency.drawHistograms();
        System.out.println("\nJAVA CONCURRENCY UTILITIES: \n");
        histogramJava.drawHistograms();
    }
}
