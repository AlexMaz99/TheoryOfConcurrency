package lab12.unordered;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Parallel;
import org.jcsp.lang.StandardChannelIntFactory;

public class PCMain {
    static int N_BUFFERS = 200;
    static final int N_ITEMS = 10000;

    public static void main(String[] args) {
        var factory = new StandardChannelIntFactory();

        var prodChannels = factory.createOne2One(N_BUFFERS);
        var consChannels = factory.createOne2One(N_BUFFERS);
        var bufferChannels = factory.createOne2One(N_BUFFERS);

        var processes = new CSProcess[N_BUFFERS + 2];
        processes[0] = new Producer(prodChannels, bufferChannels, N_ITEMS);
        processes[1] = new Consumer(consChannels, N_ITEMS);

        for (int i = 0; i < N_BUFFERS; i++)
            processes[i + 2] = new Buffer(prodChannels[i], consChannels[i], bufferChannels[i]);

        var parallel = new Parallel(processes);
        parallel.run(); // execute processes in parallel
    }
}
