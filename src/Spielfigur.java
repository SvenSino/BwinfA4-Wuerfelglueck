
public class Spielfigur {
    //Zählt die gelaufenen Schritte einer Figur
    private int schrittZaehler = -1;
    //Zeigt die Position einer Spielfigur auf dem Spielfeld
    private int position = -1;
    private String spielfigurFarbe;

    //Initialisiert eine Spielfigur mit ihrer Farbe
    public Spielfigur(String farbe) {
        this.spielfigurFarbe = farbe;
    }

    public int getSchrittZaehler() {
        return schrittZaehler;
    }

    public void setSchrittZaehler(int schrittZaehler) {
        this.schrittZaehler = schrittZaehler;
    }

    public void setSpielfigurFarbe(String spielfigurFarbe) {
        this.spielfigurFarbe = spielfigurFarbe;
    }

    public String getSpielfigurFarbe() {
        return spielfigurFarbe;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
