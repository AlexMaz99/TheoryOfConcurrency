package lab07;

import lab07.proxy.Proxy;
import lab07.proxy.ProxyBuffer;
import lab07.scheduler.Scheduler;
import lab07.servant.Buffer;

public class ActiveObject {
    private final Buffer buffer;
    private final Scheduler scheduler;
    private final Proxy proxy;

    public ActiveObject(int bufferSize) {
        this.buffer = new Buffer(bufferSize);
        this.scheduler = new Scheduler();
        this.proxy = new ProxyBuffer(scheduler, buffer);
        scheduler.start();
    }

    public Buffer getBuffer() {
        return buffer;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public Proxy getProxy() {
        return proxy;
    }
}
