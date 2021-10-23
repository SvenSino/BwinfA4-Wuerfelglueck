import java.util.ArrayList;

public class Spielfeld {
	ArrayList <Integer> spielfeld = new ArrayList<>(40);
	
	public static void run(ArrayList<Integer> spielfeld) {
		for (int i = 0; i < spielfeld.size(); i++) {
			spielfeld.add(i);
		}
		System.out.println(spielfeld);
	}

}
