import java.util.ArrayList;

public class Opponent extends Bot {
    boolean visible;

    Opponent(int x, int y, boolean visible) {
        super(y, x);

        this.x = x;
        this.y = y;
        this.visible = visible;
    }

    public void goTo(int x, int y, boolean isPacmanOnSuperMode) {
        this.x = x;
        this.y = y;

        if (!isPacmanOnSuperMode) {
            movesDid++;
        }
        else {
            movesDid--;
        }
    }

    // Die Getter Methoden (geben den Wert der Variable aus)
    boolean getVisible() {
        return visible;
    }

    // Die Setter Methoden (setzen die Variable auf einen beliebigen Wert, der eine Natürliche zahl ist)
    void setVisible(boolean visible) {
        this.visible = visible;
    }
}
