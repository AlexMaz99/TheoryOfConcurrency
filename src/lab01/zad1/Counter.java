package lab01.zad1;

class Counter {
    private int _val;

    public Counter(int n) {
        _val = n;

    }

    public void inc() {
        _val++;
    }

    public void dec() {
        _val--;
    }

    public int value() {
        return _val;
    }

}
