package seamCarving;

public class Main {

   public static void main(String[] argv) {
       if (argv.length < 4) {
           System.out.println("Usage : java -jar seamcarving.jar -ligne/-colonne <fichier source> <fichier destination> <nombre d'itération> -couleur (si ppm)");
           System.exit(0);
       }

       boolean ppm = false;
       boolean colonne = false;
       String fichierSource;
       String fichierDest;
       int nbIte;

       if (argv.length == 5) {
           if (argv[4].equals("-couleur")) {
               ppm = true;
           }
       }

       if(argv[0].equals("-colonne")) {
           colonne = true;
       }

       fichierDest = argv[2];
       fichierSource = argv[1];
        nbIte = Integer.parseInt(argv[3]);
       Pixel[][] res;

       if (ppm) {
           System.out.println("Mode PPM");
           if (colonne) {
               System.out.println("Mode colonne");
               res = SeamCarving.reduce_width(fichierSource, nbIte, true);
           } else {
               System.out.println("Mode ligne");
               res = SeamCarving.reduce_width_line(fichierSource, nbIte, true);
           }

           SeamCarving.writeppm(res, fichierDest);

       } else {
           System.out.println("Mode PGM");
           if (colonne) {
               System.out.println("Mode colonne");
               res = SeamCarving.reduce_width(fichierSource, nbIte, false);
           } else {
               System.out.println("Mode ligne");
               res = SeamCarving.reduce_width_line(fichierSource, nbIte, false);
           }

           SeamCarving.writepgm(res, fichierDest);
       }



    }
}
