package lab05.zad1;

import java.util.ArrayList;
import java.util.List;

import org.jzy3d.chart.AWTChart;
import org.jzy3d.chart.Chart;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

public class Library {
    public static void main(String[] args) throws InterruptedException {
        double semaphoresSumTime = 0;
        double locksSumTime = 0;
        double[][] semaphoreTimes = new double[100][10];
        double[][] locksTimes = new double[100][10];

        int counter = 0;
        for (int readers = 10; readers <= 100; readers += 1) {
            for (int writers = 1; writers <= 10; writers += 1) {
                counter++;
                double semaphoresTime = runReadersWritersProblem(writers, readers, new SemaphoresBook());
                semaphoresSumTime += semaphoresTime;
                semaphoreTimes[readers - 1][writers - 1] = semaphoresTime;
                double locksTime = runReadersWritersProblem(writers, readers, new LocksBook());
                locksSumTime += locksTime;
                locksTimes[readers - 1][writers - 1] = locksTime;
                if (readers % 5 == 0)
                    System.out.println("Readers: " + readers +
                            ", Writers: " + writers +
                            ", Semaphores Time: " + semaphoresTime + " ms" +
                            ", Locks Time: " + locksTime + " ms");
            }
        }

        System.out.println("\nSemaphores average Time: " + semaphoresSumTime / counter + "ms, Locks average Time: " + locksSumTime / counter + " ms");
        draw3DPlot(semaphoreTimes, 100, 10, "Semaphores");
        draw3DPlot(locksTimes, 100, 10, "Locks");
    }

    private static double measureTime(List<Thread> threads) throws InterruptedException {
        long startTime = System.nanoTime();
        threads.forEach(Thread::start);
        for (Thread thread : threads)
            thread.join();
        return (double) (System.nanoTime() - startTime) / 1000000;
    }


    private static double runReadersWritersProblem(int writers, int readers, IBook book) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < writers; i++)
            threads.add(new Writer(book));
        for (int i = 0; i < readers; i++)
            threads.add(new Reader(book));
        return measureTime(threads);
    }

    private static void draw3DPlot(double[][] table, int XLength, int YLength, String title) {
        Mapper mapper = new Mapper() {
            public double f(double x, double y) {
                if (x >= XLength || y >= YLength) return 0;
                return table[(int) x][(int) y];
            }
        };

        int steps = 50;

        final Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(new Range(0, XLength), steps, new Range(0, YLength), steps), mapper);
        surface.setFaceDisplayed(true);
        surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new Color(1, 1, 1, .5f)));
        surface.setWireframeDisplayed(false);
        surface.setWireframeColor(Color.BLACK);

        Chart chart = new AWTChart(Quality.Advanced);
        chart.add(surface);
        chart.open(title, 500, 500);

    }

}
