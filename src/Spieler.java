import javax.swing.*;
import java.lang.reflect.Array;
import java.util.*;

public class Spieler {

    private String farbe;
    private int startPunkt;
    private Wuerfel wuerfel;
    private ArrayList<Spielfigur> spielfiguren;
    private Spielfeld spielfeld;

    public Spieler(String farbe, Wuerfel wuerfel, Spielfeld spielfeld) {
        this.farbe = farbe;
        this.spielfeld = spielfeld;
        this.wuerfel = wuerfel;
        this.spielfiguren = new ArrayList<Spielfigur>();
        for (int i = 0; i < 4; i++) {
            spielfiguren.add(new Spielfigur(farbe));
        }
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

    public boolean turn() {
        int augenZahl = wuerfel.wuerfeln();
        boolean gezogen = false;
        if (augenZahl == 6) {
            Spielfigur idle = getIdle();
            if (idle != null && (spielfeld.getPosition(startPunkt) == null || spielfeld.getFarbe(startPunkt) != farbe)) {
                spielfeld.setzeFigurStart(idle, startPunkt);
                gezogen = true;
            } else if (spielfeld.getPosition(startPunkt) != null && spielfeld.getFarbe(startPunkt) == farbe) {
                if (spielfeld.ziehe(augenZahl, spielfeld.getPosition(startPunkt))) {
                    gezogen = true;
                }
            }
        }
        ArrayList<Spielfigur> figurAmZug = playOrder();
        while (!gezogen && !figurAmZug.isEmpty()) {
            Spielfigur spielfigur = figurAmZug.get(0);
            gezogen = spielfeld.ziehe(augenZahl, spielfigur);
            figurAmZug.remove(0);
        }
        figurAmZug.clear();
        if (!gewonnen())
            if (augenZahl == 6) {
                return turn();
            } else {
                return false;
            }
        return true;
    }

    public Spielfigur getIdle() {
        for (Spielfigur spielfigur : spielfiguren) {
            if (spielfigur.getInternPosition() == -1) {
                return spielfigur;
            }
        }
        return null;
    }

    public boolean gewonnen() {
        for (Spielfigur spielfigur : spielfiguren) {
            if (spielfigur.getInternPosition() < 40) {
                return false;
            }
        }
        return true;
    }


    public ArrayList<Spielfigur> playOrder() {
        ArrayList<Integer> order = new ArrayList<Integer>();
        for (Spielfigur spielfigur : spielfiguren) {
            int internPosition = spielfigur.getInternPosition();
            if (internPosition >= 0) {
                order.add(internPosition);
            }
        }
        Collections.sort(order, Collections.reverseOrder());
        ArrayList<Spielfigur> figurenOrder = new ArrayList<Spielfigur>();
        for (Integer pos : order) {
            if (pos == spielfiguren.get(0).getInternPosition()) {
                figurenOrder.add(spielfiguren.get(0));
            } else if (pos == spielfiguren.get(1).getInternPosition()) {
                figurenOrder.add(spielfiguren.get(1));
            } else if (pos == spielfiguren.get(2).getInternPosition()) {
                figurenOrder.add(spielfiguren.get(2));
            } else {
                figurenOrder.add(spielfiguren.get(3));
            }
        }
        return figurenOrder;
    }

}
