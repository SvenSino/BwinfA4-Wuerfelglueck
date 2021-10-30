import java.util.ArrayList;
import java.util.Arrays;

public class Spiel {
    //Simuliert Spiele zwischen allen W�rfelpaaren und gibt von jedem W�rfel die Gewinnraten gegen alle anderen W�rfel aus
    public static void main(String[] args) {
        FileReader reader = new FileReader();
        ArrayList<Wuerfel> wuerfelListe = reader.getWuerfel();
        int wuerfelAnzahl = wuerfelListe.size();
        //Gewinnraten jedes W�rfels gegen alle anderen W�rfel
        double[][] wuerfelGewinnraten = new double[wuerfelAnzahl][wuerfelAnzahl];
        int anzahlSpiele = 10000;
        for (int i = 0; i < wuerfelAnzahl - 1; i++) {
            for (int j = i + 1; j < wuerfelAnzahl; j++) {
                Wuerfel wuerfel1 = wuerfelListe.get(i);
                Wuerfel wuerfel2 = wuerfelListe.get(j);
                double gewinnrate = 0;
                for (int k = 0; k < anzahlSpiele/2; k++) {
                    gewinnrate += simuliereSpiel(wuerfel1, wuerfel2) + (1 - simuliereSpiel(wuerfel2, wuerfel1));
                }
                gewinnrate /= anzahlSpiele;
                wuerfelGewinnraten[i][j] = gewinnrate;
                wuerfelGewinnraten[j][i] = 1 - gewinnrate;
            }
        }
        for(int i = 0; i < wuerfelAnzahl; i++) {
            System.out.println("Wuerfel " + i + " mit den Werten" + wuerfelListe.get(i).wuerfelWerte() +  " hat folgende Gewinnchance gegen andere W�rfel: " + Arrays.toString(wuerfelGewinnraten[i]));
        }
    }

    //F�hrt ein Spiel mit 2 Spielern mit 2 gegebenen W�rfeln durch
    //Bei Sieg von Spieler 1 wird 1 zur�ckgegeben
    //Bei Sieg von Spieler 2 wird 0 zur�ckgegeben
    public static double simuliereSpiel(Wuerfel wuerfel1, Wuerfel wuerfel2) {
        Spielfeld spielfeld = new Spielfeld(2);
        Spieler spieler1 = new Spieler("schwarz", wuerfel1, spielfeld);
        Spieler spieler2 = new Spieler("gruen", wuerfel2, spielfeld);
        boolean ende = false;
        int aktiverSpieler = 0;
        while (!ende) {
            aktiverSpieler ^= 1;
            if (aktiverSpieler == 1) {
                ende = spieler1.spielzug();
            } else {
                ende = spieler2.spielzug();
            }
        }
        return aktiverSpieler;
    }
}
