package main.java.domain;

public class Tuple<R, S, T> {
    private final R r;
    private final S s;
    private final T t;

    public Tuple(R r,S s, T t) {
        this.r = r;
        this.s = s;
        this.t = t;
    }
    public R getR() {
        return r;
    }

    public S getS() {
        return s;
    }

    public T getT() {
        return t;
    }

    public String toString() {
        return r.toString() + " " + s.toString() + " " + t.toString();
    }
}
