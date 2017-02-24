package seamCarving;

import java.io.PrintStream;

public abstract class Pixel {
    public abstract int getValue();
    public abstract void print(PrintStream ps);
}
