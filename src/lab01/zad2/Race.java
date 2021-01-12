package lab01.zad2;

public class Race {

    public static void main(String[] args) throws InterruptedException {
        int[] hist = new int[2 * 10000 + 1];

        for (int i = 0; i < 100; i++) {
            Counter cnt = new Counter(0);

            DThread dThread = new DThread(cnt);
            IThread iThread = new IThread(cnt);

            dThread.start();
            iThread.start();

            dThread.join();
            iThread.join();

//            System.out.println("stan=" + cnt.value());
            hist[cnt.value() + 10000]++;
        }

        for (int i = 0; i < 2 * 10000 + 1; i++)
            if (hist[i] != 0)
                System.out.println("stan: " + (i - 10000) + ", ilosc: " + hist[i]);
    }
}
