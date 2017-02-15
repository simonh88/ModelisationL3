package seamCarving;

public class Main {

    public static void main(String[] argv) {
        int[][] res = SeamCarving.reduce_width("ex1.pgm", 200);
        SeamCarving.writepgm(res, "res/testreduce.pgm");

    }
}
