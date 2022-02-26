import java.util.ArrayList;

public class Opponent {
    int y;
    int x;
    int movesDid = 0;

    boolean visible;

    Opponent(int x, int y, boolean visible) {
        this.x = x;
        this.y = y;
        this.visible = visible;
    }

    public void goTo(int x, int y) {
        this.x = x;
        this.y = y;

        movesDid++;
    }

    // Die Getter Methoden (geben den Wert der Variable aus)
    int getX() {
        return x;
    }
    int getY() {
        return y;
    }
    int getMovesDid() {
        return movesDid;
    }
    boolean getVisible() {
        return visible;
    }

    // Die Setter Methoden (setzen die Variable auf einen beliebigen Wert, der eine Natürliche zahl ist)
    void setX(int x) {
        this.x = x;
    }
    void setY(int y) {
        this.y = y;
    }
    void setMovesDid(int movesDid) {
        this.movesDid = movesDid;
    }
    void setVisible(boolean visible) {
        this.visible = visible;
    }
}
