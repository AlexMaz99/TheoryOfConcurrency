package lab07.requests;

import lab07.future.Future;
import lab07.servant.Buffer;

public class RemoveRequest extends MethodRequest {
    private final Buffer buffer;
    private final Future future;

    public RemoveRequest(Buffer buffer, Future future) {
        this.buffer = buffer;
        this.future = future;
    }

    @Override
    public boolean guard() {
        return !buffer.isEmpty();
    }

    @Override
    public void call() {
        future.setObject(buffer.remove());
    }
}
