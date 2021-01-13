package lab12.unordered;

import org.jcsp.lang.Alternative;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Guard;
import org.jcsp.lang.One2OneChannelInt;

public class Producer implements CSProcess {
    private final One2OneChannelInt[] outChannels;
    private final One2OneChannelInt[] bufferChannels;
    private final int nItems;

    public Producer(One2OneChannelInt[] outChannels, One2OneChannelInt[] bufferChannels, int nItems) {
        this.outChannels = outChannels;
        this.bufferChannels = bufferChannels;
        this.nItems = nItems;
    }

    @Override
    public void run() {
        var guards = new Guard[bufferChannels.length];

        for (int i = 0; i < outChannels.length; i++)
            guards[i] = bufferChannels[i].in();

        var alternative = new Alternative(guards);

        for (int i = 0; i < nItems; i++) {
            var index = alternative.select();
            bufferChannels[index].in().read();

            var item = (int) (Math.random() * 100) + 1;
            System.out.println("PRODUCER: " + item);
            outChannels[index].out().write(item);
        }
    }
}
