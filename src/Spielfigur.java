
public class Spielfigur {

    private int spielfigurID;
    private int internPosition = -1;
    private int position = -1;
    private String spielfigurFarbe;

    public Spielfigur(String farbe) {
        this.spielfigurFarbe = farbe;
    }

    public int getInternPosition() {
        return internPosition;
    }

    public void setInternPosition(int internPosition) {
        this.internPosition = internPosition;
    }

    public void setSpielfigurFarbe(String spielfigurFarbe) {
        this.spielfigurFarbe = spielfigurFarbe;
    }

    public String getSpielfigurFarbe() {
        return spielfigurFarbe;
    }


    public int getSpielfigurID() {
        return spielfigurID;
    }

    public void setSpielfigurID(int spielfigurID) {
        this.spielfigurID = spielfigurID;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
