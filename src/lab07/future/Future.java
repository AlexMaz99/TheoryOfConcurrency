package lab7.future;

public class Future {
    private volatile Object object;

    public void setObject(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public boolean isAvailable() {
        return object != null;
    }
}
