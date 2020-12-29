package lab7.proxy;

import lab7.future.Future;

public interface Proxy {
    Future add(Object object);

    Future remove();
}
