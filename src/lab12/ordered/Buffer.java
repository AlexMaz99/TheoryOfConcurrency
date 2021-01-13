package lab12.ordered;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Buffer implements CSProcess {
    private final One2OneChannelInt prodChannel, consChannel;

    public Buffer(One2OneChannelInt prodChannel, One2OneChannelInt consChannel) {
        this.consChannel = consChannel;
        this.prodChannel = prodChannel;
    }

    @Override
    public void run() {
        while (true) {
            consChannel.out().write(prodChannel.in().read());
        }
    }
}
