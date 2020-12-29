package lab7;

import lab7.future.Future;
import lab7.proxy.Proxy;

import java.util.Random;

public class Producer extends Thread {
    private final Proxy proxy;
    private final String name;
    private final Random random = new Random();

    public Producer(Proxy proxy, int number) {
        this.proxy = proxy;
        this.name = "Producer " + number;
    }

    @Override
    public void run() {
        while (true) {
            int value = random.nextInt(100);
            Future future = proxy.add(value);
            while (future.isAvailable()) {
//                System.out.println(name + " is waiting");
                try {
                    sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(name + " added " + value);
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
