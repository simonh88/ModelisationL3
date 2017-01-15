package seamCarving;

import java.util.ArrayList;
import java.io.*;
import java.util.*;
public class SeamCarving
{

   public static int[][] readpgm(String fn)
	 {		
        try {
            InputStream f = ClassLoader.getSystemClassLoader().getResourceAsStream(fn);
            BufferedReader d = new BufferedReader(new InputStreamReader(f));
            String magic = d.readLine();
            String line = d.readLine();
		   while (line.startsWith("#")) {
			  line = d.readLine();
		   }
		   Scanner s = new Scanner(line);
		   int width = s.nextInt();
		   int height = s.nextInt();		   
		   line = d.readLine();
		   s = new Scanner(line);
		   int maxVal = s.nextInt();
		   int[][] im = new int[height][width];
		   s = new Scanner(d);
		   int count = 0;
		   while (count < height*width) {
			  im[count / width][count % width] = s.nextInt();
			  count++;
		   }
		   return im;
        }
		
        catch(Throwable t) {
            t.printStackTrace(System.err) ;
            return null;
        }
    }

    public static void writepgm(int[][] image, String filename) {
		try {
            int height = image.length;
            int width = image[0].length;
            int count = 0;

			OutputStream os = new FileOutputStream(filename);
			PrintStream ps = new PrintStream(os);
			// La première ligne -> magic P2
            ps.println("P2");
			// La deuxième -> largeur et hauteur
            ps.println(width + " " + height);
			// la troisème -> 255 ?
            ps.println("255");

            while (count < height*width) {
                ps.print(image[count / width][count % width] + " ");

                // Apparement, ya un saut de ligne tout les 25 nombres
                if (count % 25 == 0) {
                    ps.print("\n");
                }
                count++;
            }

            ps.close();

		} catch(Throwable t) {
			t.printStackTrace(System.err) ;
		}
	}

	public static int[][] interest(int[][] img) {
        int height = img.length;
        int width = img[0].length;
        int[][] res = new int[height][width];
        int interest_value = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // 3 cas à traiter

                // Le pixel a deux voisins
                if ( (j-1) >= 0 && (j+1) < width) {
                    // Moyenne des voisins
                    int average = ( img[i][j-1] + img[i][j+1] ) / 2;
                    // Valeur absolue de la différence entre la valeur du pixel et la moyenne de ses deux voisins
                    interest_value = Math.abs(img[i][j] - average);
                }

                // Le pixel n'a qu'un voisin à droite
                else if ( (j-1) < 0) {
                    // Valeur absolue de la différence entre la valeur du pixel et celle de son voisin droit
                    interest_value = Math.abs(img[i][j] - img[i][j+1]);
                }

                // Le pixel n'a qu'un voisin à gauche
                else {
                    // Valeur absolue de la différence entre la valeur du pixel et celle de son voisin gauche
                    interest_value = Math.abs(img[i][j] - img[i][j-1]);
                }

                res[i][j] = interest_value;
            }
        }

        return res;
    }
   
}
