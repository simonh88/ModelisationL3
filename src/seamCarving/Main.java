package seamCarving;

import java.util.Arrays;

public class Main {
    public static void main(String[] argv) {

        int[][] image = SeamCarving.readpgm("ex1.pgm");
        System.out.println("ToString : " + Arrays.deepToString(image));
        System.out.println("Largeur : " + image[0].length);
        System.out.println("Hauteur : " + image.length);
        SeamCarving.writepgm(image, "res/test.pgm");
    }
}
