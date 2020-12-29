package lab7.proxy;

import lab7.future.Future;
import lab7.requests.AddRequest;
import lab7.requests.RemoveRequest;
import lab7.scheduler.Scheduler;
import lab7.servant.Buffer;

public class ProxyBuffer implements Proxy {
    Scheduler scheduler;
    Buffer buffer;

    public ProxyBuffer(Scheduler scheduler, Buffer buffer) {
        this.scheduler = scheduler;
        this.buffer = buffer;
    }

    @Override
    public Future add(Object object) {
        Future future = new Future();
        future.setObject(object);
        scheduler.enqueue(new AddRequest(buffer, future));
        return future;
    }

    @Override
    public Future remove() {
        Future future = new Future();
        scheduler.enqueue(new RemoveRequest(buffer, future));
        return future;
    }
}
