import java.util.ArrayList;
import java.util.Random;

public class Wuerfel {

	private int anzahlSeiten;
	ArrayList<Integer> seitenWerte = new ArrayList<>();;

	public Wuerfel(String[] wuerfelData) {
		anzahlSeiten = Integer.valueOf(wuerfelData[0]);
		
		for(int i = 1; i < wuerfelData.length; i++) {
			seitenWerte.add(Integer.valueOf(wuerfelData[i]));
		}
		
	}

	public static int wuerfeln(Wuerfel wuerfel) {
		int rndWuerfel = new Random().nextInt(wuerfel.anzahlSeiten);
		return wuerfel.seitenWerte.get(rndWuerfel);

	}

	public int getAnzahlSeiten() {
		return anzahlSeiten;
	}

	public void setAnzahlSeiten(int anzahlSeiten) {
		this.anzahlSeiten = anzahlSeiten;
	}


}
