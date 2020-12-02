import java.util.Scanner;
import java.io.*;

public class Naloga3 extends LinkedList{
	public static LinkedList[] ll;

	public static void izpis(int N, String[] simboli, String izhod){
		try{
			PrintWriter pw = new PrintWriter(new File(izhod), "UTF-8");
			while(true){
				for(int i = 0; i < N; i++){
					if(ll[i].vrniPrvi() == -1){
						pw.printf("\r\n");
						pw.close();
						return;
					} else{
						pw.printf(simboli[ll[i].vrniPrvi()]);;
						ll[i].deleteNth(0);
					}
				}
			}
		}catch(IOException ex){
			System.out.printf("Datoteka %s ne obstaja!", izhod);
		}
	}
	
	public static int vrstica(int N, int k, int element){
		for(int i = 0; i < N; i++){
			if (((i+element) % N) == k) return i;
		}
		return -1;
	}

	public static void mesanje(int N, int K, int V, int P){
		int k = V;
		int I = 0;
		int e = 0;
		for(int i = 0; i < K-1; i++){
            e = ll[k].vrniZadnji();
            ll[k].deleteNth(ll[k].length() - 1);
            I = vrstica(N, k, e);
            ll[I].addFirst(e);
            k = (I - P)%N;        
            if(k < 0){
                k = k + N;
            }    
            if(k >= N) {
                k = k - N;
			}
        }

        I = 0;
        e = ll[k].vrniZadnji();
        ll[k].deleteNth(ll[k].length() - 1);
		ll[I].addFirst(e);
    }

    public static void main(String[] args){

        String vhod = args[0];
		String izhod = args[1];

        try{
			Scanner sc = new Scanner(new File(vhod));			
            String[] podatki = sc.nextLine().split(",");

            int N = Integer.parseInt(podatki[0]);
            int V = Integer.parseInt(podatki[1]);
            int K = Integer.parseInt(podatki[2]);
			int P = Integer.parseInt(podatki[3]);

			String[] simboli = {"A", "B","C","Č","D","E","F","G","H","I","J","K","L","M","N","O","P","R","S","Š","T","U","V","Z","Ž",
							"a","b","c","č","d","e","f","g","h","i","j","k","l","m","n","o","p","r","s","š","t","u","v","z","ž"," "};
			
			ll = new LinkedList[N];

            for(int i = 0; i < N; i++){
				ll[i] = new LinkedList(); 
				String[] vrstice = sc.nextLine().split(",");
				for(int j = 0; j < vrstice.length; j++){
					if(!(vrstice[j].equals("")))
						ll[i].addLast(Integer.parseInt(vrstice[j]));
				}
			}

			mesanje(N, K, V, P);
			izpis(N, simboli, izhod);
        }
        catch (FileNotFoundException ex) {
            System.out.printf("Datoteka %s ne obstaja!%n", vhod);
        }
   }
}

class LinkedListElement
{
	int element;
	LinkedListElement next;
	
	LinkedListElement(int obj)
	{
		element = obj;
		next = null;
	}
	
	LinkedListElement(int obj, LinkedListElement nxt)
	{
		element = obj;
		next = nxt;
	}
}

class LinkedList 
{
	protected LinkedListElement first;
	protected LinkedListElement last;

	protected LinkedList(LinkedListElement first){
		this.first = first;
	}
	
	LinkedList()
	{
		makenull();
	}
	
	//Funkcija makenull inicializira seznam
	public void makenull()
	{
		//drzimo se implementacije iz knjige:
		//po dogovoru je na zacetku glava seznama (header)
		first = new LinkedListElement(-1, null);
		last = null;
	}

	void addFirst(int obj)
    {
        //najprej naredimo nov element
        LinkedListElement noviElement = new LinkedListElement(obj);
        //ustrezno ga povezemo z glavo seznama
        noviElement.next = first.next;
        first.next = noviElement;
        //po potrebi posodobimo kazalca "first" in "last"
        if(last == null) {
            last = first;
        }else if(last == first) {
            last = noviElement;
        }
	}

	public void addLast(int obj)
	{
		//najprej naredimo nov element
		LinkedListElement noviElement = new LinkedListElement(obj);
		//ustrezno ga povezemo s koncem verige obstojecih elementov
		if(last == null) {
			first.next = noviElement;
			last = first;
		}else {
			last.next.next = noviElement;
			last = last.next;
		}
		//po potrebi posodobimo kazalca "first" in "last"
	}

	public int vrniZadnji(){
		if(last.next!=null) return last.next.element;
		return -1;
	}

	public int vrniPrvi(){
		if(first.next!=null) return first.next.element;
		return -1;
	}

	int length()
	{
		int s = 0;
		LinkedListElement p;
		p = first.next;
		while(p != null){
			s++;
			p = p.next;
		}
		return s;
	}
	
	boolean deleteNth(int n)
    {
        LinkedListElement el, prev_el;

        //zacnemo pri glavi seznama
        prev_el = null;
        el = first;

        //premaknemo se n-krat
        for (int i = 0; i < n; i++)
        {
            prev_el = el;
            el = el.next;
            if (el == null)
                return false;
        }

        if (el.next != null)
        {
            //preden izlocimo element preverimo, ali je potrebno popraviti kazalec "last"
            if (last == el.next) //ce brisemo predzadnji element
                last = el;
            else if (last == el) //ce bri�emo zadnji element
                last = prev_el;

            el.next = el.next.next;

            return true;
        }
        else
            return false;
    }
}
	