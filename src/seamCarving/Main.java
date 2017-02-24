package seamCarving;

public class Main {

   public static void main(String[] argv) {
       if (argv.length != 3) {
           System.out.println("Usage : java -jar seamcarving.jar <pgm source> <pgm destination> <nombre d'itération>");
           System.exit(0);
       }

       boolean ppm = true;

       Pixel[][] res;
       if (ppm) {
           res = SeamCarving.reduce_width_line(argv[0], Integer.parseInt(argv[2]), true);
           SeamCarving.writeppm(res, argv[1]);

       } else {
           res = SeamCarving.reduce_width(argv[0], Integer.parseInt(argv[2]), false);
           SeamCarving.writepgm(res, argv[1]);
       }



    }
}
