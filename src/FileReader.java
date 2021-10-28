import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {
    public  ArrayList<Wuerfel> getWuerfel() {
        // Wuerfel als Liste erstellen, Anzahl der Seiten festlegen, Werte der Seiten
        // festlegen
        try (Scanner scanner = new Scanner(Paths.get("wuerfel3.txt"))) {

            boolean firstLine = true;
            ArrayList<Wuerfel> diceList = new ArrayList<Wuerfel>();
            while (scanner.hasNextLine()) {
                if (firstLine) {
                    firstLine = false;
                    String line = scanner.nextLine();
                } else {
                    String line = scanner.nextLine();
                    String[] seiten = line.split(" ");
                    int anzahlSeiten = Integer.valueOf(seiten[0]);
                    Wuerfel wuerfel = new Wuerfel(seiten);
                    diceList.add(wuerfel);
                }
            }
            return diceList;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
            return new ArrayList<Wuerfel>();
        }
    }
}