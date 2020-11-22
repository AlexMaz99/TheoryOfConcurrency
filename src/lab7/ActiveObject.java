package lab7;

import lab7.proxy.Proxy;
import lab7.proxy.ProxyBuffer;
import lab7.scheduler.Scheduler;
import lab7.servant.Buffer;

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
