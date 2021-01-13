package lab12.unordered;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Buffer implements CSProcess {
    private final One2OneChannelInt prodChannel; // input from Producer
    private final One2OneChannelInt consChannel; // output to Consumer
    private final One2OneChannelInt more; // request for data from consumer

    public Buffer(One2OneChannelInt prodChannel, One2OneChannelInt consChannel, One2OneChannelInt more) {
        this.consChannel = consChannel;
        this.prodChannel = prodChannel;
        this.more = more;
    }

    @Override
    public void run() {
        while (true) {
            more.out().write(0); // blocks until data is available
            consChannel.out().write(prodChannel.in().read());
        }
    }
}
