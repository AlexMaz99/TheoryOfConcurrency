package lab07.proxy;

import lab07.future.Future;
import lab07.requests.AddRequest;
import lab07.requests.RemoveRequest;
import lab07.scheduler.Scheduler;
import lab07.servant.Buffer;

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
