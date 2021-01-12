package lab07.servant;

import java.util.LinkedList;
import java.util.Queue;

public class Buffer {
    private final int size;
    private final Queue<Object> elements;

    public Buffer(int size) {
        this.size = size;
        this.elements = new LinkedList<>();
    }

    public boolean isFull() {
        return elements.size() == size;
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public boolean add(Object object) {
        if (!isFull()) {
            elements.add(object);
            return true;
        }
        return false;
    }

    public Object remove() {
        if (isEmpty())
            return null;
        return elements.remove();
    }
}
