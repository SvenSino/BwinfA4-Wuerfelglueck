import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Game {

    public static void main(String[] args) {

        FileReader reader = new FileReader();
        ArrayList<Wuerfel> diceList = reader.getWuerfel();
        int wuerfelAnzahl = diceList.size();
        double[][] diceWinrates = new double[wuerfelAnzahl][wuerfelAnzahl];
        int testSize = 10000;
        for (int i = 0; i < wuerfelAnzahl - 1; i++) {
            for (int j = i + 1; j < wuerfelAnzahl; j++) {
                Wuerfel wuerfel1 = diceList.get(i);
                Wuerfel wuerfel2 = diceList.get(j);
                double winrate = 0;
                for (int k = 0; k < testSize/2; k++) {
                    winrate += simulateGame(wuerfel1, wuerfel2) + (1 - simulateGame(wuerfel2, wuerfel1));
                }
                winrate /= testSize;
                diceWinrates[i][j] = winrate;
                diceWinrates[j][i] = 1 - winrate;
            }
        }
        for(int i = 0; i < wuerfelAnzahl; i++) {
            System.out.println("Wuerfel " + i + " mit den Werten" + diceList.get(i).wuerfelWerte() +  " hat folgende Gewinnchance gegen andere Würfel: " + Arrays.toString(diceWinrates[i]));
        }
    }

    public static double simulateGame(Wuerfel wuerfel1, Wuerfel wuerfel2) {
        Spielfeld spielfeld = new Spielfeld(2);
        Spieler spieler1 = new Spieler("schwarz", wuerfel1, spielfeld);
        Spieler spieler2 = new Spieler("gruen", wuerfel2, spielfeld);
        boolean ende = false;
        int aktiverSpieler = 0;
        while (!ende) {
            aktiverSpieler ^= 1;
            if (aktiverSpieler == 1) {
                ende = spieler1.turn();
            } else {
                ende = spieler2.turn();
            }
        }
        return aktiverSpieler;
    }
}
