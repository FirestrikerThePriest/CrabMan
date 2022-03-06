import java.util.ArrayList;
import java.util.Arrays;


public class Maze {
    static final int LABYRINTH_GRÖßE = 29;

    ArrayList<Integer> theChosenOneX;
    ArrayList<Integer> theChosenOneY;

    int theChosenOnesCounter = 0;

    public void generate() {
        //System.out.println("Beginning generate"); zur Überprüfung der Funktion

        Pacman bot = new Pacman(1, 8);
        Feld feld = new Feld(LABYRINTH_GRÖßE);

        theChosenOneX = new ArrayList<>();
        theChosenOneY = new ArrayList<>();

        boolean[] kandidat = new boolean[4]; //Die vier Kandidaten 0 Oben; 1 Rechts; 2 Unten; 3 Links

        int kandidatenAnzahl = 0;
        int kandidatenNummer = 0;
        int random;
        int age = 0;
        int backAge = 0;
        int movesWithoutBacktracking = 0;

        boolean done = false;
        boolean backtracking = false;

        theChosenOnesCounter = 0;

        //Setzt jede Zelle auf Wand
        for (int x = 0; x < LABYRINTH_GRÖßE; x++) {
            for (int y = 0; y < LABYRINTH_GRÖßE; y++) {
                feld.setFieldWall(0, x, y); // hier 0 kein Kandidat die anderen immer einfach +1
                feld.setWallAge(0, x, y, LABYRINTH_GRÖßE * LABYRINTH_GRÖßE);
                feld.setVisited(0, x, y, false);
            }
        }

        // Baut den Start
        feld.setFieldCoin(0, 0, 8);
        feld.setWallAge(0, 0, 8, -1);
        feld.setFieldWall(0,0,7);
        feld.setWallAge(0, 0, 7, -1);
        feld.setFieldWall(0,0,9);
        feld.setWallAge(0, 0, 9, -1);

        // Eigentlicher Algorithmus, generiert das Labyrinth
        while (!(bot.getX() == 0 && bot.getY() == 8)) {
            //Findet die Kandidaten heraus
            for (int i = 0; i < 4; i++) {

                if (0 <= bot.kandidatPositionX(i+1) && bot.kandidatPositionX(i+1) < LABYRINTH_GRÖßE && 0 <= bot.kandidatenPositionY(i+1) && bot.kandidatenPositionY(i+1) < LABYRINTH_GRÖßE) { //Ist Kandidat innerhalb des Feldes
                    if (feld.wasNotAlreadyVisited(i+1, bot.getX(), bot.getY())) {
                        if (feld.canBeChosenAsKandidat(i+1, bot.getX(), bot.getY(), backAge)) {
                            kandidat[i] = true;
                            kandidatenNummer = i+1;

                            feld.setWallAge(i+1, bot.getX(), bot.getY(), age+1);

                            kandidatenAnzahl++;
                        }
                        else {
                            feld.setWallAge(i+1, bot.getX(), bot.getY(), -1);

                        }
                    }
                }

            }

            /*
            Bewegen des Bots auf einen der Kandidaten.
            Wenn kein Kandidat existiert, dann Backtracking.
            */

            if (kandidatenAnzahl > 1) { //kandidatenAnzahl mind. 2
                feld.setFieldCoin(0, bot.getX(), bot.getY());
                feld.setVisited(0, bot.getX(), bot.getY(), true);
                age ++;
                backAge = age;

                while (!done) {
                    random = (int) (Math.random()*4+1);

                    if (kandidat[random-1]) {
                        switch (random) {
                            case (1) -> bot.moveUp(1);
                            case (2) -> bot.moveRight(1);
                            case (3) -> bot.moveDown(1);
                            case (4) -> bot.moveLeft(1);
                        }
                        done = true;
                    }
                }
                backtracking = false;
                movesWithoutBacktracking++;
            } else if (kandidatenAnzahl == 1) {
                feld.setFieldCoin(0, bot.getX(), bot.getY());
                feld.setVisited(0, bot.getX(), bot.getY(), true);
                age++;
                backAge = age;

                switch (kandidatenNummer) {
                    case (1) -> bot.moveUp(1);
                    case (2) -> bot.moveRight(1);
                    case (3) -> bot.moveDown(1);
                    case (4) -> bot.moveLeft(1);
                    default -> System.out.println("something went wrong");
                }
                backtracking = false;
                movesWithoutBacktracking++;

            } else if (kandidatenAnzahl == 0) { // Backtracking
                feld.setFieldCoin(0, bot.getX(), bot.getY());
                feld.setVisited(0, bot.getX(), bot.getY(), true);

                if (!backtracking && movesWithoutBacktracking > 3) {
                    random = (int) (Math.random()*30+1);
                    if (random == 1) {
                        feld.setFieldTheChosenOne(0, bot.getX(), bot.getY());
                        //System.out.println("a chosen one!"); //zur Überprüfung der Funktion

                        theChosenOneX.add(theChosenOnesCounter, bot.getX());
                        theChosenOneY.add(theChosenOnesCounter, bot.getY());

                        theChosenOnesCounter++;
                    }
                }

                backAge--;
                bot.moveBackTo(backAge);

                backtracking = true;
                movesWithoutBacktracking = 0;
            } else {
                System.out.println("something bad happened");
            }

            Arrays.fill(kandidat, false);

            done = false;

            kandidatenAnzahl = 0;
            kandidatenNummer = 0;
        }

        //System.out.println("finished Generating"); zur Überprüfung der Funktion
    }

    public void shuffleTheChosenOnes(int NumberOfShuffles) {
        int randomIndexOne;
        int randomIndexTwo;
        int zwischenSpeicherX;
        int zwischenSpeicherY;

        for (int i = 0; i < NumberOfShuffles; i++) {
            if (!(theChosenOnesCounter <= 0)) {
                randomIndexOne = (int) (Math.random() * theChosenOnesCounter);
                randomIndexTwo = (int) (Math.random() * theChosenOnesCounter);

                zwischenSpeicherX = theChosenOneX.get(randomIndexOne);
                zwischenSpeicherY = theChosenOneY.get(randomIndexOne);

                theChosenOneX.set(randomIndexOne, theChosenOneX.get(randomIndexTwo));
                theChosenOneY.set(randomIndexOne, theChosenOneY.get(randomIndexTwo));

                theChosenOneX.set(randomIndexTwo, zwischenSpeicherX);
                theChosenOneY.set(randomIndexTwo, zwischenSpeicherY);
            }
            else {
                generate();
            }
        }
    }

    // Die Getter Methoden
    public int getTheChosenOnesCounter() {
        return theChosenOnesCounter;
    }
    public int getTheChosenOneX(int index) {
        return theChosenOneX.get(index);
    }
    public int getTheChosenOneY(int index) {
        return theChosenOneY.get(index);
    }

    // Die Setter Methoden
    public void setTheChosenOnesCounter(int theChosenOnesCounter) {
        this.theChosenOnesCounter = theChosenOnesCounter;
    }
    public void setTheChosenOneX(int index, int theChosenOneX) {
        this.theChosenOneX.set(index, theChosenOneX);
    }
    public void setTheChosenOneY(int index, int theChosenOneY) {
        this.theChosenOneY.set(index, theChosenOneY);
    }

}
