import java.util.Scanner;
import java.io.*;

public class Naloga2{ 
    private static int prva;
    private static int druga;
    private static int najD = 0;
    private static String[] zacasno;
    private static String[] naj;
    private static int tmp = 0;
    private static int[] koord = new int[2];

    public static void izpis(int x, int y, String izhod, int dolzina){
        try {
            FileWriter iz = new FileWriter(izhod);
            iz.write(koord[0] + "," + koord[1]);
            iz.write("\r\n");
            for(int i = 0; i < dolzina-1; i++){   
                iz.write(naj[i]);
                iz.write(",");
            }
            iz.write(naj[dolzina-1]);
            iz.write("\r\n");
            iz.close();
        }
        catch (IOException ex){
            System.out.println("Prišlo je do napake.");
        }
    }

	private static boolean jeObiskano(String mat[][], int[][] obiskano, int x, int y)
	{
		return (obiskano[x][y] != 0) ? false : true;
	}

	public static int najdaljsaPot(String matrika[][], int[][] obiskano, int i, int j, int max_dist, int dist, int dol, int sir){
        
		obiskano[i][j] = 1;

		if (i < dol-1 && jeObiskano(matrika, obiskano, i+1, j) && matrika[i+1][j].equals(matrika[i][j])) {
            zacasno[tmp] = "DOL";
            tmp++;
            max_dist = najdaljsaPot(matrika, obiskano,i + 1, j, max_dist, dist + 1, dol, sir);
            tmp--;
		}

		if (j < sir-1 && jeObiskano(matrika, obiskano, i, j+1) && matrika[i][j+1].equals(matrika[i][j])) {
            zacasno[tmp] = "DESNO";
            tmp++;
            max_dist = najdaljsaPot(matrika, obiskano, i, j + 1, max_dist, dist + 1, dol, sir);
            tmp--;
		}

		if (i > 0 && jeObiskano(matrika, obiskano, i-1,j) && matrika[i-1][j].equals(matrika[i][j])) {
            zacasno[tmp] = "GOR";
            tmp++;
            max_dist = najdaljsaPot(matrika, obiskano, i - 1, j, max_dist, dist + 1, dol, sir);
            tmp--;
		}

		if (j > 0 && jeObiskano(matrika, obiskano, i,j-1) && matrika[i][j-1].equals(matrika[i][j])) {
            zacasno[tmp] = "LEVO";
            tmp++;
            max_dist = najdaljsaPot(matrika, obiskano, i, j - 1, max_dist, dist + 1, dol, sir);
            tmp--;
		}

        obiskano[i][j] = 0;
        
        if(najD < max_dist){
            najD = max_dist;
            naj = zacasno.clone();
        }
        
		return Math.max(max_dist, dist);
	}


    public static void main(String[] args){
 
        try{
            Scanner sc = new Scanner(new File(args[0]));
            String[] dimenzije = sc.nextLine().split(",");
            
            prva = Integer.parseInt(dimenzije[0]);
            druga = Integer.parseInt(dimenzije[1]);

            String[][] matrika = new String[prva][druga];
        
            for(int i = 0; i < prva; i++){
                matrika[i] = sc.nextLine().split(",");
            }

            int najPot = 0;
            int pot = 0;
            int[][] obiskano = new int[prva][druga];
            zacasno = new String[prva*druga];
            naj = new String[prva*druga];
            for(int i = 0; i < prva; i++){
                for(int j = 0; j < druga; j++){
                    pot = najdaljsaPot(matrika, obiskano, i, j, 0, 0, prva, druga);
                    if(najPot < pot){
                        najPot = pot;
                        koord[0] = i;
                        koord[1] = j;
                    }
                }
            }
            izpis(koord[0], koord[1], args[1], najPot);
        }
        catch (FileNotFoundException ex) {
            System.out.printf("Datoteka %s ne obstaja!%n", args[0]);
        }
    }
}
