package lab08;

import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;

public class Mandelbrot implements Callable {
    private static int MAX_ITER = 570;
    private final double ZOOM = 400;
    private BufferedImage image;
    private double zx, zy, cX, cY, tmp;
    private int width;
    private int height;

    public Mandelbrot(BufferedImage bufferedImage, int width, int height) {
        this.image = bufferedImage;
        this.width = width;
        this.height = height;
    }

    public Integer call() {
        for (int x = 0; x < width; x++) {
            zx = zy = 0;
            cX = (x - 400) / ZOOM;
            cY = (height - 300) / ZOOM;
            int iter = MAX_ITER;
            while (zx * zx + zy * zy < 4 && iter > 0) {
                tmp = zx * zx - zy * zy + cX;
                zy = 2.0 * zx * zy + cY;
                zx = tmp;
                iter--;
            }
            image.setRGB(x, height, iter | (iter << 8));
        }
        return 1;
    }

    public static void setMAX_ITER(int maxIter) {
        MAX_ITER = maxIter;
    }
}
