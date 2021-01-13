package lab12.unordered;

import org.jcsp.lang.Alternative;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Guard;
import org.jcsp.lang.One2OneChannelInt;

public class Consumer implements CSProcess {
    private final One2OneChannelInt[] inChannels;
    private final int nItems;

    public Consumer(One2OneChannelInt[] inChannels, int nItems) {
        this.inChannels = inChannels;
        this.nItems = nItems;
    }

    @Override
    public void run() {
        var startTime = System.nanoTime();
        var guards = new Guard[inChannels.length];

        for (int i = 0; i < inChannels.length; i++)
            guards[i] = inChannels[i].in();

        var alternative = new Alternative(guards);
        for (int i = 0; i < nItems; i++) {
            var index = alternative.select();
            var item = inChannels[index].in().read();
            System.out.println("CONSUMER: " + item);
        }

        System.out.println("Time: " + (System.nanoTime() - startTime) / 1000000 + "ms");
        System.exit(0);
    }
}
