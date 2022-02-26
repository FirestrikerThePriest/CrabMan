import java.util.ArrayList;

/**
 * Pacman ist ein Bot, der sich im Feld bewegt und das Labyrinth generiert.
 * außerdem ist er Pacman der sich später als spielfigur im Labyrinth
 * bewegt. Sei nett zu ihm :)
 */
public class Pacman extends Bot {
    ArrayList<Integer> movesX = new ArrayList<>();
    ArrayList<Integer> movesY = new ArrayList<>();

    int angle; // 0 = hoch; 1 = rechts; 2 = unten; 3 = links

    boolean superMode = false;

    // Konstruktor
    Pacman(int x, int y) {
        super(x, y);

        movesX.add(0);
        movesY.add(8);

        angle = 0;
    }

    // Methode fürs Backtracking
    public void moveBackTo(int where) {
        x = movesX.get(where);
        y = movesY.get(where);
    }

    // Methoden zum Horizontalen oder Vertikalen Bewegen
    public void moveUp(int move) {
        movesX.add(x);
        movesY.add(y);

        add(0, -move);
        movesDid++;
    }
    public void moveRight(int move) {
        movesX.add(x);
        movesY.add(y);

        add(move, 0);
        movesDid++;
    }
    public void moveDown(int move) {
        movesX.add(x);
        movesY.add(y);

        add(0, move);
        movesDid++;
    }
    public void moveLeft(int move) {
        movesX.add(x);
        movesY.add(y);

        add(-move, 0);
        movesDid++;
    }

    // Gibt die X/Y Koordinaten des Felds eins Horizontal oder Vertikal daneben aus.
    // Die Kandidaten zum Labyrinth generieren
    public int kandidatPositionX(int kandidat) {
        int newX = x;

        switch (kandidat){
            case (2) -> newX = x+1;
            case (4) -> newX = x-1;
        }

        return newX;
    }
    public int kandidatenPositionY(int kandidat) {
        int newY = y;

        switch (kandidat){
            case (1) -> newY = y-1;
            case (3) -> newY = y+1;
        }

        return newY;
    }

    // Getter Methoden
    int getAngle() {
        return angle;
    }
    int getMovesX(int index) {
        return movesX.get(index);
    }
    int getMovesY(int index) {
        return movesY.get(index);
    }
    int getLengthMovesX() {
        return movesX.size();
    }
    int getLengthMovesY() {
        return movesY.size();
    }
    boolean getSuperMode() {
        return superMode;
    }

    // Setter Methoden
    void setAngle(int angle) {
        this.angle = angle;
    }
    void setMovesX(int index, int moveX) {
        movesX.set(index, moveX);
    }
    void setMovesY(int index, int moveY) {
        movesY.set(index, moveY);
    }
    void setSuperMode(boolean superMode) {
        this.superMode = superMode;
    }
}
