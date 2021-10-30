import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Wuerfel {
	private int anzahlSeiten;
	ArrayList<Integer> seitenWerte = new ArrayList<>();;

	//Initialisiert einen Würfel anhand der eingelesenen Daten
	public Wuerfel(String[] wuerfelData) {
		anzahlSeiten = Integer.valueOf(wuerfelData[0]);
		for(int i = 1; i < wuerfelData.length; i++) {
			seitenWerte.add(Integer.valueOf(wuerfelData[i]));
		}
	}

	//Gibt 6 zurück, wenn es eine 6 gibt und ansonsten die größte Augenzahl
	public int getStartAugenzahl() {
		return seitenWerte.contains(6) ? 6 : Collections.max(seitenWerte);
	}

	public int wuerfeln() {
		int rndWuerfel = new Random().nextInt(this.anzahlSeiten);
		return seitenWerte.get(rndWuerfel);
	}

	public ArrayList<Integer> wuerfelWerte() {
		return seitenWerte;
	}

	public String wuerfelWerteString() {
		 return seitenWerte.toString();
	}
}
