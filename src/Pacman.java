import java.util.ArrayList;

/**
 * Pacman ist ein Bot, der sich im Feld bewegt und das Labyrinth generiert.
 * außerdem ist er Pacman der sich später als spielfigur im Labyrinth
 * bewegt. Sei nett zu ihm :)
 */
public class Pacman {
    ArrayList<Integer> movesX = new ArrayList<>();
    ArrayList<Integer> movesY = new ArrayList<>();

    int angle; // 0 = hoch; 1 = rechts; 2 = unten; 3 = links
    int y;
    int x;
    int movesDid = 0;

    boolean superMode = false;

    Pacman(int x, int y) {
        this.x = x;
        this.y = y;

        movesX.add(0);
        movesY.add(8);

        angle = 0;
    }

    public void add(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void moveBackTo(int where) {
        x = movesX.get(where);
        y = movesY.get(where);
    }

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

    // Die Getter Methoden (geben den Wert der Variable aus)
    int getX() {
        return x;
    }
    int getY() {
        return y;
    }
    int getAngle() {
        return angle;
    }
    int getMovesDid() {
        return movesDid;
    }
    int getMovesX(int index) {
        return movesX.get(index);
    }
    int getMovesY(int index) {
        return movesY.get(index);
    }
    boolean getSuperMode() {
        return superMode;
    }

    // Die Setter Methoden (setzen die Variable auf einen beliebigen Wert, der eine Natürliche zahl ist)
    void setX(int x) {
        this.x = x;
    }
    void setY(int y) {
        this.y = y;
    }
    void setAngle(int angle) {
        this.angle = angle;
    }
    void setMovesDid(int movesDid) {
        this.movesDid = movesDid;
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
