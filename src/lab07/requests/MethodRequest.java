package lab7.requests;

public abstract class MethodRequest {
    // check if the condition for executing the method is met
    public abstract boolean guard();

    // execute the method on the original object
    public abstract void call();
}
