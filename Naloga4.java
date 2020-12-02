import java.util.Scanner;
import java.io.*;

class LinkedListElement{
    int[] element;
    LinkedListElement next;

    LinkedListElement(){
        element = null;
        next = null;
    }
}

class LinkedList{
    protected LinkedListElement first;
    protected int velikost;
    
    LinkedList(){
        makenull();
    }

    public void makenull(){
        first = null;
        velikost = 0;
    }

    public void init(int size){
        velikost = size;
    }

    public boolean zeObstaja(int id){
        LinkedListElement novo = first;
        while(novo != null){
            if(novo.element[0] == id) return true;
            novo = novo.next;
        }
        return false;
    }
    
    public boolean alloc(int size, int id){

        if(zeObstaja(id)) return false;
       
        LinkedListElement novo = first;
        LinkedListElement n = new LinkedListElement();

        if(first == null || (first != null && first.element[1] >= size)){
            if (size > velikost) return false;
            int[] podatki = {id, 0, size-1, size};
            n.element = podatki;
            first = n;
            first.next = novo; 
            return true;
        }
        
        while(novo != null){
            if((novo.next == null && (velikost - novo.element[2] >= size)) || (novo.next != null && (novo.next.element[1] - novo.element[2]) > size)){
                int[] podatki = {id, novo.element[2] + 1, novo.element[2] + size, size};
                n.element = podatki;
                n.next = novo.next;
                novo.next = n;
                return true;
            }
            novo = novo.next;
        }
        return false;
    }
    
    public int free(int id){ 
        LinkedListElement novi = first;
        LinkedListElement prejsnji = new LinkedListElement();
        if(novi != null && novi.element[0] == id){
            first = first.next;
            return novi.element[3];
        }
        
        while(novi != null && novi.element[0] != id){
            prejsnji = novi;
            novi = novi.next;
        }
        if (novi != null){
            prejsnji.next = novi.next;
            return novi.element[3];
        } 
        return 0;
    }
    
    public void defrag(int n){
        LinkedListElement tmp = first;
        int konec = 0;
        while(n > 0 && tmp != null){
            int[] trenutna = tmp.element.clone();
            tmp.element[1] = konec;
            tmp.element[2] = tmp.element[1] + tmp.element[3] -1;
            konec = tmp.element[2] +1;
            if (trenutna[1] == tmp.element[1] && trenutna[2] == tmp.element[2]) n++;
            tmp = tmp.next;
            n--;
        }
    }
}

public class Naloga4{

    public static void izpis(LinkedList ll, String izhod){
        try {
            FileWriter iz = new FileWriter(izhod);
            while(ll.first != null){ 
                iz.write(ll.first.element[0] + "," + ll.first.element[1] + "," + ll.first.element[2]);
                iz.write("\r\n");
                ll.first = ll.first.next;
            }
            iz.close();
        }
        catch (IOException ex){
            System.out.println("Prišlo je do napake.");
        }
    }

    public static void main(String args[]){
        String vhod = args[0];
        String izhod = args[1];
        LinkedList ll = new LinkedList();
        try{
            Scanner sc = new Scanner(new File(vhod));
            int n = Integer.parseInt(sc.nextLine());
            
            String[] prva = sc.nextLine().split(",");
            int inic = Integer.parseInt(prva[1]);
            ll.init(inic);

            int tmp = n-1;
            while(tmp > 0){
                String[] vrstica = sc.nextLine().split(",");
                switch(vrstica[0]){
                    case "a":
                        ll.alloc(Integer.parseInt(vrstica[1]), Integer.parseInt(vrstica[2]));
                        break;
                    case "f":
                        ll.free(Integer.parseInt(vrstica[1]));
                        break;
                    case "d":
                        ll.defrag(Integer.parseInt(vrstica[1]));
                        break;
                    default:
                        System.out.println("Napačen vhod!");
                }
                tmp--;
            }
            izpis(ll, izhod);
        }
        catch (FileNotFoundException ex) {
            System.out.printf("Datoteka %s ne obstaja!%n", vhod);
        }
    }
}