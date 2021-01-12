package lab05.zad1;

public class Writer extends Thread {
    private final IBook book;

    public Writer(IBook book) {
        this.book = book;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                this.book.write();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
