package lab05.zad2;

public interface INode {
    boolean contains(Object o) throws InterruptedException;

    INode remove(Object o) throws InterruptedException;

    boolean add(Object o) throws InterruptedException;

    void print();
}
