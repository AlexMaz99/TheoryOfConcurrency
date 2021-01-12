package lab07.proxy;

import lab07.future.Future;

public interface Proxy {
    Future add(Object object);

    Future remove();
}
