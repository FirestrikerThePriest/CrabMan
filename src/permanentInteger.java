import java.io.*;

public class permanentInteger {
    int value;

    permanentInteger() {
        readValue();
    }

    public void setValue(int value) {
        this.value = value;

        writeValue();
    }

    public int getValue() {
        readValue();

        return value;
    }

    private void writeValue() {
        try {
            FileWriter writer;
            writer = new FileWriter("C:\\Users\\lenna\\Documents\\Freizeit\\Java\\Dateien fuer Programme\\Programm Crabman\\highscore.txt");

            writer.write(value + "");

            writer.close();

            //System.out.println("Deine Mutter ist arg Fett weil das sollte nicht passieren du hurensohn..."); zur überprüfung der Funktion
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readValue() {
        try {
            BufferedReader reader;
            reader = new BufferedReader(new FileReader("C:\\Users\\lenna\\Documents\\Freizeit\\Java\\Dateien fuer Programme\\Programm Crabman\\highscore.txt"));

            value = Integer.parseInt(reader.readLine());

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
