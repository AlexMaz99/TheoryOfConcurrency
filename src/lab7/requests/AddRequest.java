package lab7.requests;

import lab7.future.Future;
import lab7.servant.Buffer;

public class AddRequest extends MethodRequest {
    private final Buffer buffer;
    private final Future future;

    public AddRequest(Buffer buffer, Future future) {
        this.buffer = buffer;
        this.future = future;
    }

    @Override
    public boolean guard() {
        return !buffer.isFull();
    }

    @Override
    public void call() {
        if (buffer.add(future.getObject()))
            future.setObject(null);
    }
}
