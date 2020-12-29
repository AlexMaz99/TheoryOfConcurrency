package lab8;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MandelbrotMain extends JFrame {
    private BufferedImage image;

    public static void main(String[] args) {
        new MandelbrotMain();
    }

    public MandelbrotMain() {
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        for (int i = 200; i < 3000; i += 300) {
            executeTests(i);
        }
        numberOfThreadsTest();
    }

    private void numberOfThreadsTest() {
        Mandelbrot.setMAX_ITER(570);
        for (int i = 1; i < 30; i += 2) {
            System.out.println("Number of threads / maximum number of threads: " + i);
            run(Executors.newFixedThreadPool(i), "FixedThreadPool");
            run(Executors.newWorkStealingPool(i), "WorkStealingPool");
            System.out.println("\n");
        }
    }

    private void executeTests(int maxIter) {
        Mandelbrot.setMAX_ITER(maxIter);
        System.out.println("Max iterations: " + maxIter);
        run(Executors.newSingleThreadExecutor(), "SingleThreadExecutor");
        run(Executors.newFixedThreadPool(10), "FixedThreadPool");
        run(Executors.newCachedThreadPool(), "CachedThreadPool");
        run(Executors.newWorkStealingPool(), "WorkStealingPool");
        System.out.println("-------------------------------\n");
    }

    private void run(ExecutorService executorService, String executorName) {
        image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        List<Future<Integer>> futures = new ArrayList<>();
        long time = measureTime(futures, executorService);
        System.out.println(executorName + ", time: " + time + " ms");
        setVisible(true);
    }

    private long measureTime(List<Future<Integer>> futures, ExecutorService executorService) {
        long startTime = System.currentTimeMillis();

        for (int height = 0; height < getHeight(); height++) {
            Mandelbrot mandelbrot = new Mandelbrot(image, getWidth(), height);
            futures.add(executorService.submit(mandelbrot));
        }

        AtomicInteger sum = new AtomicInteger();
        futures.forEach(future -> {
            try {
                sum.addAndGet(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        return System.currentTimeMillis() - startTime;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }
}
