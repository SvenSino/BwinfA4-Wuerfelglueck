import java.nio.file.Paths;
import java.util.Scanner;

public class FileReader {
	public static void readFile() {
		// Wuerfel als Liste erstellen, Anzahl der Seiten festlegen, Werte der Seiten
		// festlegen
		try (Scanner scanner = new Scanner(Paths.get("wuerfel3.txt"))) {

//			String anzahlWuerfel = scanner.next();
//			System.out.println(anzahlWuerfel);
			boolean firstLine = true;
			while (scanner.hasNextLine()) {
				if (firstLine) {
					firstLine = false;
					String line = scanner.nextLine();
				} else {
					String line = scanner.nextLine();
					String[] seiten = line.split(" ");
					int anzahlSeiten = Integer.valueOf(seiten[0]);
					Wuerfel wuerfel = new Wuerfel(seiten);


					System.out.println(anzahlSeiten + " seitiger Wuerfel hat gewürfelt: " + Wuerfel.wuerfeln(wuerfel));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: " + e.getMessage());
		}
	}
}