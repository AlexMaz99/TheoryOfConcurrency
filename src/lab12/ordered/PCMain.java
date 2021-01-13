package lab12.ordered;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Parallel;
import org.jcsp.lang.StandardChannelIntFactory;

public class PCMain {
    static final int N_BUFFERS = 10;
    static final int N_ITEMS = 10000;

    public static void main(String[] args) {
        var factory = new StandardChannelIntFactory();

        var channels = factory.createOne2One(N_BUFFERS + 1); // Consumer requests

        var processes = new CSProcess[N_BUFFERS + 2];
        processes[0] = new Producer(channels[0], N_ITEMS);
        processes[1] = new Consumer(channels[N_BUFFERS], N_ITEMS);

        for (int i = 0; i < N_BUFFERS; i++)
            processes[i + 2] = new Buffer(channels[i], channels[i + 1]);

        var parallel = new Parallel(processes);
        parallel.run();
    }
}
