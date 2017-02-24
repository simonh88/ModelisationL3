package seamCarving;

import java.io.PrintStream;

public class PixelRGB extends Pixel {

    private int r;
    private int g;
    private int b;



    public PixelRGB(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getValue() {
        return r + g + b;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public void print(PrintStream ps ) {
        ps.print(r + " " + g + " " + b + " ");
    }
}
