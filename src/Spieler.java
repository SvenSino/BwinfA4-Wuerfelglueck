import java.util.*;

public class Spieler {
    private String farbe;
    private int startPunkt;
    //Falls ein Würfel keine 6 hat, um aus einem Startfeld zu ziehen
    private int startAugenzahl;
    private Wuerfel wuerfel;
    private ArrayList<Spielfigur> spielfiguren;
    private Spielfeld spielfeld;

    //Initialisiert einen Spieler mit dem Spielfeld, seiner Farbe und seinem Wuerfel
    public Spieler(String farbe, Wuerfel wuerfel, Spielfeld spielfeld) {
        this.farbe = farbe;
        this.spielfeld = spielfeld;
        this.wuerfel = wuerfel;
        this.startAugenzahl = wuerfel.getStartAugenzahl();
        this.spielfiguren = new ArrayList<Spielfigur>();
        for (int i = 0; i < 4; i++) {
            spielfiguren.add(new Spielfigur(farbe));
        }
        //Legt den Startpunkt eines Spielers abhängig von seiner Farbe fest
        if (farbe.equals("schwarz")) {
            this.startPunkt = 0;
        } else if (farbe.equals("gelb")) {
            this.startPunkt = 10;
        } else if (farbe.equals("gruen")) {
            this.startPunkt = 20;
        } else {
            this.startPunkt = 30;
        }
        spielfeld.setzeFigurStart(spielfiguren.get(0), startPunkt);
    }

    //Simuliert einen Spielzug
    public boolean spielzug() {
        int augenZahl = wuerfel.wuerfeln();
        boolean gezogen = false;
        //Wenn der Würfelwurf gleich der startAugenzahl ist, wird zuerst geprüft, ob eine Figur aufs Spielfeld gesetzt werden kann
        //oder ansonsten eine Spielfigur von der Startposition bewegt werden kann
        if (augenZahl == startAugenzahl) {
            Spielfigur idle = getIdle();
            if (idle != null && (spielfeld.getSpielfigur(startPunkt) == null || spielfeld.getFarbe(startPunkt) != farbe)) {
                spielfeld.setzeFigurStart(idle, startPunkt);
                gezogen = true;
            } else if (spielfeld.getSpielfigur(startPunkt) != null && spielfeld.getFarbe(startPunkt) == farbe) {
                if (spielfeld.ziehe(augenZahl, spielfeld.getSpielfigur(startPunkt))) {
                    gezogen = true;
                }
            }
        }
        //Anhand der Priorität der Spielfiguren wird ein möglicher Zug geprüft
        ArrayList<Spielfigur> figurAmZug = playOrder();
        while (!gezogen && !figurAmZug.isEmpty()) {
            Spielfigur spielfigur = figurAmZug.get(0);
            gezogen = spielfeld.ziehe(augenZahl, spielfigur);
            figurAmZug.remove(0);
        }
        //Beim Wurf der Startaugenzahl wird im Fall eines Weiterspielens noch ein Zug durchgeführt, sonst wird false zurückgegeben
        if (!gewonnen())
            if (augenZahl == startAugenzahl) {
                return spielzug();
            } else {
                return false;
            }
        //Bei Sieg wird true zurückgegeben
        return true;
    }

    //Gibt eine Liste der Spielfiguren auf den Startfeldern zurück
    public Spielfigur getIdle() {
        for (Spielfigur spielfigur : spielfiguren) {
            if (spielfigur.getSchrittZaehler() == -1) {
                return spielfigur;
            }
        }
        return null;
    }

    //Prüft anhand der Zielfelder, ob das Spiel gewonnen wurde
    public boolean gewonnen() {
        for (Spielfigur spielfigur : spielfiguren) {
            if (spielfigur.getSchrittZaehler() < 40) {
                return false;
            }
        }
        return true;
    }

    //Gibt eine Liste der Figuren sortiert nach ihrer Zugpriorität aus
    public ArrayList<Spielfigur> playOrder() {
        ArrayList<Integer> order = new ArrayList<Integer>();
        for (Spielfigur spielfigur : spielfiguren) {
            int internPosition = spielfigur.getSchrittZaehler();
            if (internPosition >= 0) {
                order.add(internPosition);
            }
        }
        Collections.sort(order, Collections.reverseOrder());
        ArrayList<Spielfigur> figurenOrder = new ArrayList<Spielfigur>();
        for (Integer pos : order) {
            if (pos == spielfiguren.get(0).getSchrittZaehler()) {
                figurenOrder.add(spielfiguren.get(0));
            } else if (pos == spielfiguren.get(1).getSchrittZaehler()) {
                figurenOrder.add(spielfiguren.get(1));
            } else if (pos == spielfiguren.get(2).getSchrittZaehler()) {
                figurenOrder.add(spielfiguren.get(2));
            } else {
                figurenOrder.add(spielfiguren.get(3));
            }
        }
        return figurenOrder;
    }

}
