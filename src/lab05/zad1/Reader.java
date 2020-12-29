package lab5.zad1;

public class Reader extends Thread {
    private final IBook book;

    public Reader(IBook book) {
        this.book = book;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                this.book.read();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
