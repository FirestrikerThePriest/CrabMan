import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyPanel extends JPanel implements ActionListener{
    final int LABYRINTH_GRÖßE = 29;
    final int ZELLEN_GRÖßE = 30;
    final int PANEL_WIDTH = ZELLEN_GRÖßE*(LABYRINTH_GRÖßE+2);  // 930
    final int PANEL_HEIGHT = ZELLEN_GRÖßE*(LABYRINTH_GRÖßE+2); // 930

    Timer timer;
    Feld feld;
    Maze maze;
    Pacman pacman;
    Opponent opponent;

    int realAngle;
    int frameCount = 0;
    int punkte = 0;
    int level = 0;
    int subLevel = 0;

    boolean auf = false;
    boolean move = true;
    boolean opponentMove = true;
    boolean gameOver = false;
    boolean noMoreChosenOnes = false;

    MyPanel() {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.black); //new Color(100, 65, 164)
        this.setOpaque(true);

        timer = new Timer(40, this);
        timer.start();

        feld = new Feld(LABYRINTH_GRÖßE);
        maze = new Maze();
        pacman = new Pacman(0, 9);
        opponent = new Opponent(0,9, false);

        pacman.setAngle(2);
        pacman.setMovesX(0, 0);
        pacman.setMovesY(0, 9);

        maze.generate();
        maze.shuffleTheChosenOnes(100);
    }

    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2D = (Graphics2D) (g);

        if (!gameOver) {

            g2D.setPaint(Color.BLUE);
            // Malt eine Border um das Labyrinth
            for (int i = 0; i < LABYRINTH_GRÖßE + 2; i++) {
                g2D.fillRect(i * ZELLEN_GRÖßE, 0, ZELLEN_GRÖßE, ZELLEN_GRÖßE);
            }
            for (int i = 0; i < LABYRINTH_GRÖßE + 2; i++) {
                g2D.fillRect(i * ZELLEN_GRÖßE, PANEL_HEIGHT - ZELLEN_GRÖßE, ZELLEN_GRÖßE, ZELLEN_GRÖßE);
            }
            for (int i = 0; i < LABYRINTH_GRÖßE + 2; i++) {
                g2D.fillRect(0, i * ZELLEN_GRÖßE, ZELLEN_GRÖßE, ZELLEN_GRÖßE);
            }
            for (int i = 0; i < LABYRINTH_GRÖßE + 2; i++) {
                g2D.fillRect(PANEL_HEIGHT - ZELLEN_GRÖßE, i * ZELLEN_GRÖßE, ZELLEN_GRÖßE, ZELLEN_GRÖßE);
            }

            // Erzeugt den Eingang
            g2D.setPaint(Color.black);
            g2D.fillRect(0, 270, ZELLEN_GRÖßE, ZELLEN_GRÖßE);

            // Malt das Labyrinth
            for (int i = 0; i < LABYRINTH_GRÖßE; i++) {
                for (int j = 0; j < LABYRINTH_GRÖßE; j++) {
                    if (feld.isFieldCoin(0, i, j)) {
                        // Malt die Münzen
                        g2D.setPaint(Color.yellow);
                        g2D.fillOval((i + 1) * ZELLEN_GRÖßE + 10, (j + 1) * ZELLEN_GRÖßE + 10, 10, 10);

                    } else if (feld.isFieldWall(0, i, j)) {
                        // Malt die Wände
                        g2D.setPaint(Color.BLUE);
                        g2D.fillRect((i + 1) * ZELLEN_GRÖßE, (j + 1) * ZELLEN_GRÖßE, ZELLEN_GRÖßE, ZELLEN_GRÖßE);

                    } else if (feld.isFieldTheChosenOne(0, i, j)) {
                        // Malt eine Münze, die übermalt wird, wenn ein TheChosenOne gemalt wird
                        g2D.setPaint(Color.yellow);
                        g2D.fillOval((i + 1) * ZELLEN_GRÖßE + 10, (j + 1) * ZELLEN_GRÖßE + 10, 10, 10);

                        // Malt den TheChosenOne der nach dem SubLevel jetzt angezeigt werden muss
                        if (!noMoreChosenOnes) {
                            if (maze.getTheChosenOneX(subLevel) == i && maze.getTheChosenOneY(subLevel) == j) {
                                g2D.setPaint(Color.green);
                                g2D.fillRect((i + 1) * ZELLEN_GRÖßE, (j + 1) * ZELLEN_GRÖßE, ZELLEN_GRÖßE, ZELLEN_GRÖßE);
                            }
                        }

                    }
                }
            }

            // Malt Pacman
            // der Angle wird, richtig eingestellt
            switch (pacman.getAngle()) {
                case (1) -> realAngle = 1;
                case (2) -> realAngle = 0;
                case (3) -> realAngle = 3;
                case (4) -> realAngle = 2;
            }

            // Malt Pacman mit mund auf und zu
            g2D.setPaint(Color.yellow);
            if (auf) {
                g2D.fillArc(pacman.getX() * ZELLEN_GRÖßE + 1, pacman.getY() * ZELLEN_GRÖßE + 1, 28, 28, 45 + realAngle * 90, 270);
            } else {
                g2D.fillOval(pacman.getX() * ZELLEN_GRÖßE + 1, pacman.getY() * ZELLEN_GRÖßE + 1, 28, 28);
            }

            // Punkte Anzahl
            g2D.setPaint(Color.yellow);
            g2D.drawString("Punkte: " + punkte, 10, 25);

            // Opponent malen
            g2D.setPaint(Color.red);
            g2D.fillOval(opponent.getX()*ZELLEN_GRÖßE+3, opponent.getY()*ZELLEN_GRÖßE+3, 24, 24);

        }
        else {
            g2D.setPaint(Color.red);
            g2D.setFont(new Font("MV Boli", Font.BOLD, 100));

            g2D.drawString("GAME OVER!", 130, 470);
        } // Game Over screen

    } // Malt

    @Override
    public void actionPerformed(ActionEvent e) { // Im Prinzip Main Loop, der die Logik ausführt und dann nach jeder iteration die paint methode aufruft
        // Pacman läuft nicht in Wände
        try {
            if (feld.isFieldWall(pacman.getAngle(), pacman.getX() - 1, pacman.getY() - 1)) {
                move = false;
            }
        }
        catch (ArrayIndexOutOfBoundsException exception) {
            move=false;
        }

        // Pacman bewegen
        if (frameCount%10 == 0 && move) {
            switch (pacman.getAngle()) {
                case (1) -> pacman.moveUp(1);
                case (2) -> pacman.moveRight(1);
                case (3) -> pacman.moveDown(1);
                case (4) -> pacman.moveLeft(1);
            }
        }
        // Mund von Pacman auf und zu machen
        if (frameCount%6 == 0) {
            auf = false;
        }
        else if (frameCount%3 == 0){
            auf = true;
        }

        // Münzen fressen
        if (feld.isFieldCoin(0, pacman.getX()-1, pacman.getY()-1)) {
            feld.clearField(0, pacman.getX()-1, pacman.getY()-1);
            punkte++;
        }

        // auf the chosen ones reagieren
        if (feld.isFieldTheChosenOne(0, pacman.getX()-1, pacman.getY()-1)) {
            feld.clearField(0, pacman.getX()-1, pacman.getY()-1);

            pacman.setSuperMode(true);

        }

        // Wird Gegner angezeigt und beginnt sich zu bewegen
        if (pacman.getMovesDid() > 3) {
            opponent.setVisible(true);
        }

        // Gegner wird bewegt
        if (opponent.getVisible() && opponentMove) {
            if (frameCount%11 == 0) {
                try {
                    opponent.goTo(pacman.getMovesX(opponent.getMovesDid()), pacman.getMovesY(opponent.getMovesDid()));
                    //System.out.println("Opponent moved to: X: " + pacman.getMovesX(opponent.getMovesDid()) + " Y: " + pacman.getMovesY(opponent.getMovesDid()));
                }
                catch (Exception exception) {
                    System.out.println("Error");
                    opponent.goTo(pacman.getX(), pacman.getY());
                }
            }
        }

        // Gegner tötet oder wird gefressen
        if (opponent.getX() == pacman.getX() && opponent.getY() == pacman.getY()) {
            System.out.println("Getroffen");
            if (!pacman.getSuperMode()) {
                // aktionen um den GameOver screen zu erzeugen
                gameOver = true;
                move = false;
                opponentMove = false;
            }
            else {
                punkte += 50;
                pacman.setSuperMode(false);

                while (opponent.getX() != maze.getTheChosenOneX(subLevel) && opponent.getY() != maze.getTheChosenOneY(subLevel)) {
                    opponent.goTo(pacman.getMovesX(opponent.getMovesDid()), pacman.getMovesY(opponent.getMovesDid()));
                }

                subLevel++;

            }
        }

        // Nachschauen, ob es noch theChosenOnes gibt, um eine ArrayIndexOutOfBound Exception zu vermeiden
        if (subLevel == maze.getTheChosenOnesCounter()) {
            noMoreChosenOnes = true;
        }

        frameCount++;
        repaint();
    }

    public void setPacmanAngle(int pacmanAngle) {
        pacman.setAngle(pacmanAngle);
    }
    public void setMove(boolean newMoveValue) {
        move = newMoveValue;
    }
}