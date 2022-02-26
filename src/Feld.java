
public class Feld {
    enum FeldZustand {
        MÜNZE, WAND, GEGNERBESIEGUNGSPUNKT, NICHTS
    }
    int LABYRINTH_GRÖßE;

    static FeldZustand[][] zellen;
    boolean[][] visited;
    int[][] wallAge;

    public Feld(int größe) {
        LABYRINTH_GRÖßE = größe;

        zellen = new Feld.FeldZustand[LABYRINTH_GRÖßE][LABYRINTH_GRÖßE];
        visited = new boolean[LABYRINTH_GRÖßE][LABYRINTH_GRÖßE];
        wallAge = new int[LABYRINTH_GRÖßE][LABYRINTH_GRÖßE];

    }

    public boolean wasNotAlreadyVisited(int kandidat, int botPositionX, int botPositionY) {
        switch (kandidat){
            case (1) -> botPositionY--;
            case (2) -> botPositionX++;
            case (3) -> botPositionY++;
            case (4) -> botPositionX--;
        }

        return !visited[botPositionX][botPositionY];
    }

    public boolean canBeChosenAsKandidat(int kandidat, int botPositionX, int botPositionY, int backAge) {
        switch (kandidat){
            case (1) -> botPositionY--;
            case (2) -> botPositionX++;
            case (3) -> botPositionY++;
            case (4) -> botPositionX--;
        }

        return backAge <= wallAge[botPositionX][botPositionY];
    }

    // Die Getter Methoden

    public int getWallAge(int botPostionX, int botPostionY) {
        return wallAge[botPostionX][botPostionY];
    }
    public boolean getVisited(int botPositionX, int botPositionY) {
        return visited[botPositionX][botPositionY];
    }
    public boolean isFieldCoin(int kandidat, int botPositionX, int botPositionY) {
        switch (kandidat){
            case (1) -> botPositionY--;
            case (2) -> botPositionX++;
            case (3) -> botPositionY++;
            case (4) -> botPositionX--;
        }

        return zellen[botPositionX][botPositionY] == FeldZustand.MÜNZE;
    }
    public boolean isFieldWall(int kandidat, int botPositionX, int botPositionY) {
        switch (kandidat){
            case (1) -> botPositionY--;
            case (2) -> botPositionX++;
            case (3) -> botPositionY++;
            case (4) -> botPositionX--;
        }

        return zellen[botPositionX][botPositionY] == FeldZustand.WAND;
    }
    public boolean isFieldTheChosenOne(int kandidat, int botPositionX, int botPositionY) {
        switch (kandidat){
            case (1) -> botPositionY--;
            case (2) -> botPositionX++;
            case (3) -> botPositionY++;
            case (4) -> botPositionX--;
        }

        return zellen[botPositionX][botPositionY] == FeldZustand.GEGNERBESIEGUNGSPUNKT;
    }
    public boolean isFieldEmpty(int kandidat, int botPositionX, int botPositionY) {
        switch (kandidat){
            case (1) -> botPositionY--;
            case (2) -> botPositionX++;
            case (3) -> botPositionY++;
            case (4) -> botPositionX--;
        }

        return zellen[botPositionX][botPositionY] == FeldZustand.NICHTS;
    }

    // Die Setter Methoden

    public void setWallAge(int kandidat, int botPositionX, int botPositionY, int neuerWert) {
        switch (kandidat){
            case (1) -> botPositionY--;
            case (2) -> botPositionX++;
            case (3) -> botPositionY++;
            case (4) -> botPositionX--;
        }

        wallAge[botPositionX][botPositionY] = neuerWert;
    }
    public void setVisited(int kandidat, int botPositionX, int botPositionY, boolean neuerWert) {
        switch (kandidat){
            case (1) -> botPositionY--;
            case (2) -> botPositionX++;
            case (3) -> botPositionY++;
            case (4) -> botPositionX--;
        }

        visited[botPositionX][botPositionY] = neuerWert;

    }
    public void setFieldCoin(int kandidat, int botPositionX, int botPositionY) {
        switch (kandidat){
            case (1) -> botPositionY--;
            case (2) -> botPositionX++;
            case (3) -> botPositionY++;
            case (4) -> botPositionX--;
        }

        zellen[botPositionX][botPositionY] = FeldZustand.MÜNZE;
    }
    public void setFieldWall(int kandidat, int botPositionX, int botPositionY) {
        switch (kandidat){
            case (1) -> botPositionY--;
            case (2) -> botPositionX++;
            case (3) -> botPositionY++;
            case (4) -> botPositionX--;
        }

        zellen[botPositionX][botPositionY] = FeldZustand.WAND;
    }
    public void setFieldTheChosenOne(int kandidat, int botPositionX, int botPositionY) {
        switch (kandidat){
            case (1) -> botPositionY--;
            case (2) -> botPositionX++;
            case (3) -> botPositionY++;
            case (4) -> botPositionX--;
        }

        zellen[botPositionX][botPositionY] = FeldZustand.GEGNERBESIEGUNGSPUNKT;
    }
    public void clearField(int kandidat, int botPositionX, int botPositionY) {
        switch (kandidat){
            case (1) -> botPositionY--;
            case (2) -> botPositionX++;
            case (3) -> botPositionY++;
            case (4) -> botPositionX--;
        }

        zellen[botPositionX][botPositionY] = FeldZustand.NICHTS;
    }

}