package cgxf.gobang;

public class SixTuple {
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;

    public SixTuple() {

    }

    public SixTuple(int a, int b, int c, int d, int e, int f) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return Integer.toString(this.a) + Integer.toString(this.b) + Integer.toString(this.c) + Integer.toString(this.d)
                + Integer.toString(this.e) + Integer.toString(this.f);
    }
}

