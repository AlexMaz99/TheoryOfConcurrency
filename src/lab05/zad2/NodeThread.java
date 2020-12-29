package lab5.zad2;

import java.util.List;

public class NodeThread extends Thread{
    private INode node;
    private final List<Object> objects;

    public NodeThread(INode node, List<Object> objects){
        this.node = node;
        this.objects = objects;
    }

    @Override
    public void run() {
        objects.forEach(object -> {
            try {
                node.add(object);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                node.contains(object);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (Thread.currentThread().isInterrupted())
                return;
        });
        for (int i = 1; i < objects.size(); i += 3){
            try {
                node = node.remove(objects.get(i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                node.contains(objects.get(i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (Thread.currentThread().isInterrupted())
                return;
        }
    }
}
