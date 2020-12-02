import java.util.Scanner;
import java.io.*;

public class Naloga1 {
    private static int minCena = 100000000;
    private static int[] naj;
    private static int[] zacasno;

    public static void izpis(int[] n, String izhod){
        int dol = 0;

        for(int tmp = 0; tmp < n.length; tmp++){
            if(n[tmp] != 0 && n[tmp] != -1){
                n[dol] = n[tmp];
                dol++;
            }
        }

        try {
            FileWriter iz = new FileWriter(izhod);
            for(int i = 0; i < dol; i++){   
                iz.write(Integer.toString(n[i]));
                if(i != dol-1){
                    iz.write(",");
                }
            }
            iz.write("\r\n");
            iz.close();
        }
        catch (IOException ex){
            System.out.println("Prišlo je do napake.");
        }
        
    }

   public static int izbira(int d, int g, int trenutnoGorivo, int n, int[] razdalje, int[] cene,String izhod, int ix, int skpCena, int flag){
        if(minCena < skpCena) return 10000000;
        
        if(trenutnoGorivo < 0) return 10000000;
        
        zacasno[ix] = flag;
        
        if(n==ix){
            if(skpCena <= minCena){
                minCena = skpCena;
                naj = zacasno.clone();
            }
            return 0;
        } 
            
        int cena = (g-trenutnoGorivo)*cene[ix];

        int polnimo = cena + izbira(d,g,g-razdalje[ix+1],n,razdalje,cene,izhod,ix+1,skpCena + cena, ix+1);
        int nePolnimo = izbira(d,g,trenutnoGorivo-razdalje[ix+1],n,razdalje,cene,izhod,ix+1,skpCena, -1);
        
        return (polnimo < nePolnimo) ? polnimo : nePolnimo;
    }

    public static void main(String args[]){
        String vhod = args[0];
        String izhod = args[1];

        try{
            Scanner sc = new Scanner(new File(vhod));   
            String[] vhodniP = sc.nextLine().split(",");

            // Zapišemo prvo vrstico
            int d = Integer.parseInt(vhodniP[0]);
            int g = Integer.parseInt(vhodniP[1]);
            int n = Integer.parseInt(vhodniP[2]);

            // Zapišemo informacije o črpalkah
            String[] index = new String[n];
            int[] razdalje = new int[n+2];
            int[] cene = new int[n];

            for(int i = 0; i < n; i++){
                index[i] = sc.nextLine();
                index[i] = index[i].substring(index[i].lastIndexOf(":")+1);
                String[] tmp = index[i].split(",");
                razdalje[i] = Integer.parseInt(tmp[0]);
                cene[i] = Integer.parseInt(tmp[1]); 
            }
            
            // za pridobitev razdalje med zadnjo postajo in koncem
            int s = 0;
            for(int i = 0; i < n; i++){			
                s+=razdalje[i];	
            }	
            razdalje[n] = d-s;

            naj = new int[n+1];
            zacasno = new int[n+1];

            int postaje = (izbira(d,g,g-razdalje[0],n,razdalje,cene,izhod, 0, 0, 0));
            izpis(naj,izhod);
        }
        catch (FileNotFoundException ex) {
            System.out.printf("Datoteka %s ne obstaja!%n", vhod);
        }
    }
}