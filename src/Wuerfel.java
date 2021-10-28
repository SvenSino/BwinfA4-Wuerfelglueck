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

	public int wuerfeln() {
		int rndWuerfel = new Random().nextInt(this.anzahlSeiten);
		return seitenWerte.get(rndWuerfel);

	}

	public String wuerfelWerte() {
		 return seitenWerte.toString();
	}

	public int getAnzahlSeiten() {
		return anzahlSeiten;
	}

	public void setAnzahlSeiten(int anzahlSeiten) {
		this.anzahlSeiten = anzahlSeiten;
	}


}
