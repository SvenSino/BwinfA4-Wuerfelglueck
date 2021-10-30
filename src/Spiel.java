import java.util.ArrayList;
import java.util.Arrays;

public class Spiel {
    //Simuliert Spiele zwischen allen Würfelpaaren und gibt von jedem Würfel die Gewinnraten gegen alle anderen Würfel aus
    public static void main(String[] args) {
        FileReader reader = new FileReader();
        ArrayList<Wuerfel> wuerfelListe = reader.getWuerfel();
        int wuerfelAnzahl = wuerfelListe.size();
        //Gewinnraten jedes Würfels gegen alle anderen Würfel
        int[][] wuerfelGewinnraten = new int[wuerfelAnzahl][wuerfelAnzahl];
        int anzahlSpiele = 5000;
        for (int i = 0; i < wuerfelAnzahl - 1; i++) {
            for (int j = i + 1; j < wuerfelAnzahl; j++) {
                Wuerfel wuerfel1 = wuerfelListe.get(i);
                Wuerfel wuerfel2 = wuerfelListe.get(j);
                double gewinnrate = 0;
                for (int k = 0; k < anzahlSpiele / 2; k++) {
                    gewinnrate += simuliereSpiel(wuerfel1, wuerfel2) + (1 - simuliereSpiel(wuerfel2, wuerfel1));
                }
                gewinnrate = Math.round((gewinnrate/anzahlSpiele)*100);
                wuerfelGewinnraten[i][j] = (int)gewinnrate;
                wuerfelGewinnraten[j][i] = 100 - (int)gewinnrate;
            }
        }
        System.out.println("Gewinnchancen der Würfel in Prozent");

        for (int i = 0; i < wuerfelAnzahl; i++) {
            System.out.println("Wuerfel " + i + ": " +  Arrays.toString(wuerfelGewinnraten[i]) +" mit den Werten " + wuerfelListe.get(i).wuerfelWerte());
        }
        System.out.println("Diagonale Werte sind 0.0, da die Würfel nicht gegen sich selbst spielen.");
    }

    //Führt ein Spiel mit 2 Spielern mit 2 gegebenen Würfeln durch
    //Bei Sieg von Spieler 1 wird 1 zurückgegeben
    //Bei Sieg von Spieler 2 wird 0 zurückgegeben
    public static double simuliereSpiel(Wuerfel wuerfel1, Wuerfel wuerfel2) {
        Spielfeld spielfeld = new Spielfeld(2);
        Spieler spieler1 = new Spieler("schwarz", wuerfel1, spielfeld);
        Spieler spieler2 = new Spieler("gruen", wuerfel2, spielfeld);
        boolean ende = false;
        int zugZaehler = 0;
        int aktiverSpieler = 0;
        while (!ende) {
            aktiverSpieler ^= 1;
            //Wenn ein Spiel mehr als 500 Züge dauert, wird es als Unentschieden gewertet(Um Endlosschleifen zu vermeiden)
            //Alle 50 Züge wird geprüft, ob kein Zug mehr möglich ist(Dann Unentschieden)
            if(zugZaehler == 500 || (zugZaehler % 50 == 0 && !spieler1.istZugMoeglich() && !spieler2.istZugMoeglich())) {
                return 0.5;
            }
            if (aktiverSpieler == 1) {
                ende = spieler1.spielzug();
            } else {
                ende = spieler2.spielzug();
            }
            zugZaehler++;
        }
        return aktiverSpieler;
    }
}
