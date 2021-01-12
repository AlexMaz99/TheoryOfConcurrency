package lab07;

import lab07.future.Future;
import lab07.proxy.Proxy;

public class Consumer extends Thread {
    private final Proxy proxy;
    private final String name;

    public Consumer(Proxy proxy, int number) {
        this.proxy = proxy;
        this.name = "Consumer " + number;
    }

    @Override
    public void run() {
        while (true) {
            Future future = proxy.remove();
            while (!future.isAvailable()) {
//                System.out.println(name + " is waiting");
                try {
                    sleep(80);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " consumed " + future.getObject());
        }
    }
}
