package lab2.zad3;

public class Client extends Thread{
    private CountingSemaphore countingSemaphore;

    public Client (CountingSemaphore countingSemaphore){
        this.countingSemaphore = countingSemaphore;
    }

    @Override
    public void run() {
        try {
            countingSemaphore.P();
            System.out.println(countingSemaphore.getVal());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            countingSemaphore.V();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
