package seamCarving;

import java.io.PrintStream;

public class PixelBN extends Pixel{

    private int value;

    public PixelBN(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void print(PrintStream ps ) {
        ps.print(value + " ");
    }
}
