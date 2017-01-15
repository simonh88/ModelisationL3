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
   
}
