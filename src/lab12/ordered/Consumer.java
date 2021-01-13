package lab12.ordered;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Consumer implements CSProcess {
    private final One2OneChannelInt inChannel;
    private final int nItems;

    public Consumer(One2OneChannelInt inChannel, int nItems) {
        this.inChannel = inChannel;
        this.nItems = nItems;
    }

    @Override
    public void run() {
        var startTime = System.nanoTime();

        for (int i = 0; i < nItems; i++) {
            var item = inChannel.in().read();
            System.out.println("CONSUMER: " + item);
        }

        System.out.println("TIME: " + (System.nanoTime() - startTime) / 1000000 + "ms");
        System.exit(0);
    }
}
