package seamCarving;

import java.util.Arrays;

public class Main {
    public static void main(String[] argv) {

        System.out.println("\n[*] Test de readpgm");
        int[][] image = SeamCarving.readpgm("ex1.pgm");
        System.out.println("[I] Largeur : " + image[0].length);
        System.out.println("[I] Hauteur : " + image.length);
        System.out.println("\n[*] Test de write pgm, voir res/test.pgm");
        SeamCarving.writepgm(image, "res/test.pgm");

        System.out.println("\n[*] Test de interest");
        int[][] pix_interest = SeamCarving.interest(image);
        System.out.println("[I] ToString : " + Arrays.deepToString(pix_interest));
        System.out.println("[I] Largeur : " + pix_interest[0].length);
        System.out.println("[I] Hauteur : " + pix_interest.length);
    }
}
