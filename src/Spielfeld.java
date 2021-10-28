import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Spielfeld {
    ArrayList<Spielfigur> spielfeld;
    ArrayList<Spielfigur> zielfeldRot;
    ArrayList<Spielfigur> zielfeldSchwarz;
    ArrayList<Spielfigur> zielfeldGruen;
    ArrayList<Spielfigur> zielfeldGelb;

    public Spielfeld(int spielerzahl) {
        this.spielfeld = new ArrayList<Spielfigur>(40);
        for (int i = 0; i < 40; i++) {
            spielfeld.add(i, null);
        }
        this.zielfeldSchwarz = new ArrayList<Spielfigur>(4);
        for (int i = 0; i < 4; i++) {
            zielfeldSchwarz.add(i, null);
        }
        this.zielfeldGruen = new ArrayList<Spielfigur>(4);
        for (int i = 0; i < 4; i++) {
            zielfeldGruen.add(i, null);
        }
        if (spielerzahl > 2) {
            this.zielfeldRot = new ArrayList<Spielfigur>(4);
            for (int i = 0; i < 4; i++) {
                zielfeldRot.add(i, null);
            }
            if (spielerzahl > 3) {
                this.zielfeldGelb = new ArrayList<Spielfigur>(4);
                for (int i = 0; i < 4; i++) {
                    zielfeldGelb.add(i, null);
                }
            }
        }
    }


    public boolean ziehe(int schritte, Spielfigur spielfigur) {
        int startPos = spielfigur.getPosition();
        int startInternPos = spielfigur.getInternPosition();
        int zielInternPos = startInternPos + schritte;
        if (zielInternPos > 43) {
            return false;
        } else if (40 <= zielInternPos && zielInternPos <= 43) {
            int zielPos = zielInternPos - 40;
            if (setzeFigurZiel(spielfigur, startPos, zielPos)) {
                if (startPos < 40) {
                    spielfeld.set(startPos, null);
                }
                return true;
            } else {
                return false;
            }
        } else {
            int zielPos = (startPos + schritte) % 40;
            if (setzeFigur(spielfigur, zielPos, zielInternPos)) {
                spielfeld.set(startPos, null);
                return true;
            }
        }
        return false;
    }
// hier irgendwo fehler suchen startintpos
    public boolean setzeFigur(Spielfigur spielfigur1, int pos, int internPosition) {
        Spielfigur spielfigur2 = getPosition(pos);
        if (spielfigur2 == null || spielfigur1.getSpielfigurFarbe() != spielfigur2.getSpielfigurFarbe()) {
            spielfigur1.setPosition(pos);
            spielfigur1.setInternPosition(internPosition);
            if (spielfigur2 != null) {
                spielfigur2.setPosition(-1);
                spielfigur2.setInternPosition(-1);
            }
            spielfeld.set(pos, spielfigur1);
            return true;
        }
        return false;
    }

    public boolean setzeFigurZiel(Spielfigur spielfigur1, int startPos, int zielPos) {
        String farbe = spielfigur1.getSpielfigurFarbe();
        ArrayList<Spielfigur> zielfeld;
        if (farbe.equals("schwarz")) {
            zielfeld = zielfeldSchwarz;
        } else if (farbe.equals("gelb")) {
            zielfeld = zielfeldGelb;
        } else if (farbe.equals("gruen")) {
            zielfeld = zielfeldGruen;
        } else {
            zielfeld = zielfeldRot;
        }
        Spielfigur spielfigur2 = zielfeld.get(zielPos);
        if (spielfigur2 == null) {
            spielfigur1.setInternPosition(zielPos + 40);
            spielfigur1.setPosition(zielPos + 40);
            zielfeld.set(zielPos, spielfigur1);
            return true;
        }
        return false;
    }


    public String getFarbe(int position) {
        if (spielfeld == null) {
            return "frei";
        } else {
            return getPosition(position).getSpielfigurFarbe();
        }
    }

    public void setzeFigurStart(Spielfigur figur, int position) {
        Spielfigur geschlagen = getPosition(position);
        if (geschlagen != null) {
            geschlagen.setInternPosition(-1);
            geschlagen.setPosition(-1);
        }
        spielfeld.set(position, figur);
        figur.setPosition(position);
        figur.setInternPosition(0);
    }

    public Spielfigur getPosition(int position) {
        return spielfeld.get(position);
    }

}
