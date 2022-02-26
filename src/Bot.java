public class Bot {
    int y;
    int x;
    int movesDid = 0;
    int speed;

    public Bot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void add(int x, int y) {
        this.x += x;
        this.y += y;
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
    int getSpeed() {
        return speed;
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
    void setSpeed(int speed) {
        this.speed = speed;
    }
}
