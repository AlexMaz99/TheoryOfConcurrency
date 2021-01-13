package lab12.ordered;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Producer implements CSProcess {
    private final One2OneChannelInt outChannel;
    private final int nItems;

    public Producer(One2OneChannelInt outChannel, int nItems) {
        this.outChannel = outChannel;
        this.nItems = nItems;
    }

    @Override
    public void run() {
        for (int i = 0; i < nItems; i++) {
            var item = (int) (Math.random() * 100) + 1;
            System.out.println("PRODUCER: " + item);
            outChannel.out().write(item);
        }
    }
}
