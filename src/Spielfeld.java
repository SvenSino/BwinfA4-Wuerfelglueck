import java.util.ArrayList;

public class Spielfeld {
    ArrayList<Spielfigur> spielfeld;
    ArrayList<Spielfigur> zielfeldRot;
    ArrayList<Spielfigur> zielfeldSchwarz;
    ArrayList<Spielfigur> zielfeldGruen;
    ArrayList<Spielfigur> zielfeldGelb;

    //Initialisiert das Spielfeld und die Zielfelder mithilfe der Spieleranzahl
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

    //Versucht eine Spielfigur zu bewegen.
    //Gibt bei Erfolg true zurück, sonst false
    public boolean ziehe(int schritte, Spielfigur spielfigur) {
        int startPos = spielfigur.getPosition();
        int startInternPos = spielfigur.getSchrittZaehler();
        int zielInternPos = startInternPos + schritte;
        //Prüft ob die Spielfigur mit dem Wurf auf einem Zielfeld oder einer Position auf dem Spielfeld landet
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
            if (setzeFigurSpielfeld(spielfigur, zielPos, zielInternPos)) {
                spielfeld.set(startPos, null);
                return true;
            }
        }
        return false;
    }

    //Prüft ob eine Figur sich auf das gegebene Feld auf dem Spielbrett(Zielfelder ausgeschlossen) bewegen kann und tut dies gegebenenfalls
    //Beim Schlag einer Figur wird diese auf ihr Startfeld zurückgesetzt
    public boolean setzeFigurSpielfeld(Spielfigur spielfigur1, int pos, int internPosition) {
        Spielfigur spielfigur2 = getSpielfigur(pos);
        if (spielfigur2 == null || spielfigur1.getSpielfigurFarbe() != spielfigur2.getSpielfigurFarbe()) {
            spielfigur1.setPosition(pos);
            spielfigur1.setSchrittZaehler(internPosition);
            if (spielfigur2 != null) {
                spielfigur2.setPosition(-1);
                spielfigur2.setSchrittZaehler(-1);
            }
            spielfeld.set(pos, spielfigur1);
            return true;
        }
        return false;
    }

    //Prüft ob eine Figur sich auf das gegebene Feld auf seinem Zielfeld bewegen kann
    //und tut dies gegebenenfalls
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
            spielfigur1.setSchrittZaehler(zielPos + 40);
            spielfigur1.setPosition(zielPos + 40);
            zielfeld.set(zielPos, spielfigur1);
            if (startPos > 39) {
                zielfeld.set(startPos - 40, null);
            }
            return true;
        }
        return false;
    }

    //Gibt die Farbe der Spielfigur auf dem Feld an, falls dort eine steht
    public String getFarbe(int position) {
        if (spielfeld == null) {
            return "frei";
        } else {
            return getSpielfigur(position).getSpielfigurFarbe();
        }
    }

    //Setzt eine Figur auf ihr Startfeld
    public void setzeFigurStart(Spielfigur figur, int position) {
        Spielfigur geschlagen = getSpielfigur(position);
        if (geschlagen != null) {
            geschlagen.setSchrittZaehler(-1);
            geschlagen.setPosition(-1);
        }
        spielfeld.set(position, figur);
        figur.setPosition(position);
        figur.setSchrittZaehler(0);
    }

    //Gibt die Spielfigur an der Position zurück
    public Spielfigur getSpielfigur(int position) {
        return spielfeld.get(position);
    }

    //Prüft ob ein Spieler einen Zug durchführen kann
    public boolean istZugMoeglich(Wuerfel wuerfel, ArrayList<Spielfigur> spielfiguren, int startPunkt) {
        boolean figurInStartfeld = false;
        String farbe = spielfiguren.get(0).getSpielfigurFarbe();
        ArrayList<Spielfigur> spielfigurenSpielfeld = new ArrayList<Spielfigur>();
        //Prüft ob sich mindestens eine Figur auf dem Startfeld befindet und fügt restliche einer Menge von Spielfiguren auf dem Spielfeld hinzu
        for (Spielfigur spielfigur : spielfiguren) {
            if (spielfigur.getSchrittZaehler() == -1) {
                figurInStartfeld = true;
            } else {
                spielfigurenSpielfeld.add(spielfigur);
            }
        }
        //Falls das Startfeld belegbar ist, wird true zurückgegeben
        if (figurInStartfeld && (getSpielfigur(startPunkt) == null || getFarbe(startPunkt) != farbe)) {
            return true;
        }
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
        //Prüft für jede Spielfigur auf dem Spielfeld, ob sie sich fortbewegen könnte und gibt bei einem Treffer true aus
        for (Spielfigur spielfigur : spielfigurenSpielfeld) {
            for (int augenZahl : wuerfel.wuerfelWerte()) {
                int startPos = spielfigur.getPosition();
                int startInternPos = spielfigur.getSchrittZaehler();
                int zielInternPos = startInternPos + augenZahl;
                //Prüft ob die Spielfigur mit dem Wurf auf einem Zielfeld oder einer Position auf dem Spielfeld landet
                if (40 <= zielInternPos && zielInternPos <= 43) {
                    int zielPos = zielInternPos - 40;
                    if (zielfeld.get(zielPos) == null) {
                        return true;
                    }
                } else if (zielInternPos < 40) {
                    int zielPos = (startPos + augenZahl) % 40;
                    if (spielfeld.get(zielPos) == null || spielfeld.get(zielPos).getSpielfigurFarbe() != spielfigur.getSpielfigurFarbe()) {
                        return true;
                    }
                }
            }
        }
        //Kein Zug möglich

        return false;
    }
}
