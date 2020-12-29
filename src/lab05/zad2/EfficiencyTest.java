package lab5.zad2;

import java.util.ArrayList;
import java.util.List;

public class EfficiencyTest {
    public static void main(String[] args) throws InterruptedException {
        List<Object> objects = getObjects();
        double oneLockNodeSum = 0;
        double manyLocksNodeSum = 0;

        int counter = 0;
        int numberOfThreads = 10;

        // time in milliseconds
        for (int time = 0; time < 200; time += 10){
            counter ++;
            OneLockNode.setSleepTime(time);
            ManyLocksNode.setSleepTime(time);

            INode oneLockNode = new OneLockNode("start", null);
            INode manyLocksNode = new ManyLocksNode("start", null);

            double oneLockNodeTime = executeTests(oneLockNode, objects, numberOfThreads);
            oneLockNodeSum += oneLockNodeTime;
            double manyLocksNodeTime = executeTests(manyLocksNode, objects, numberOfThreads);
            manyLocksNodeSum += manyLocksNodeTime;

            System.out.println("Sleep time: " + time + " Many locks: " + manyLocksNodeTime + " One lock: " + oneLockNodeTime);
        }
        System.out.println("\nMany locks average time: " + manyLocksNodeSum / counter + " One lock average time: " + oneLockNodeSum / counter);
    }

    private static double executeTests(INode node, List<Object> objects, int numberOfThreads) throws InterruptedException {
        List<NodeThread> threads = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; i++){
            threads.add(new NodeThread(node, objects));
        }

        long startTime = System.nanoTime();
        threads.forEach(NodeThread::start);
        for (NodeThread thread: threads)
            thread.join();

        return (double) (System.nanoTime() - startTime) / 1000000;
    }

    private static List<Object> getObjects(){
        List<Object> objects = new ArrayList<>();
        for(int i = 0; i < 25; i++){
            objects.add(i * 11);
        }
        return objects;
    }
}
