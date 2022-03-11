import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.Arrays;

public class MyPanel extends JPanel implements ActionListener {
    final int LABYRINTH_GRÖßE = 29;
    final int ZELLEN_GRÖßE = 30;
    final int PANEL_WIDTH = ZELLEN_GRÖßE * (LABYRINTH_GRÖßE + 2);  // 930
    final int PANEL_HEIGHT = ZELLEN_GRÖßE * (LABYRINTH_GRÖßE + 2); // 930

    Timer timer;
    Feld feld;
    Maze maze;
    Pacman pacman;
    Opponent opponent;
    Music music;
    Soundeffekte soundeffekte;

    int realAngle;
    int frameCount = 0;
    int punkte = 0;
    int level = 0;
    int subLevel = 0;

    permanentInteger highscore = new permanentInteger();

    double distance;

    boolean auf = false;
    boolean move = false;
    boolean opponentMove = false;
    boolean gameOver = true;
    boolean noMoreChosenOnes = false;
    boolean opponentWasAlreadyAtTheChosenOne = false;
    boolean canGoToNextLevel = false;
    boolean controllingEnabled = false;
    boolean showCoins = false;
    boolean gameJustStartet = true;

    boolean[][] visited = new boolean[LABYRINTH_GRÖßE][LABYRINTH_GRÖßE];

    public MyPanel() {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.black); //new Color(100, 65, 164)
        this.setOpaque(true);

        music = new Music();
        soundeffekte = new Soundeffekte();

        timer = new Timer(40, this);
        timer.start();

        feld = new Feld(LABYRINTH_GRÖßE);
        maze = new Maze();
        pacman = new Pacman(0, 9);
        opponent = new Opponent(0, 9, false);

        pacman.setAngle(2);
        pacman.setMovesX(0, 0);
        pacman.setMovesY(0, 9);
        pacman.setSpeed(9);

        maze.generate();
        maze.shuffleTheChosenOnes(100);
    }

    @Override
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
                        if (showCoins) {
                            g2D.setPaint(Color.yellow);
                            g2D.fillOval((i + 1) * ZELLEN_GRÖßE + 10, (j + 1) * ZELLEN_GRÖßE + 10, 10, 10);
                        }

                    } else if (feld.isFieldWall(0, i, j)) {
                        // Malt die Wände
                        g2D.setPaint(Color.BLUE);
                        g2D.fillRect((i + 1) * ZELLEN_GRÖßE, (j + 1) * ZELLEN_GRÖßE, ZELLEN_GRÖßE, ZELLEN_GRÖßE);

                    } else if (feld.isFieldTheChosenOne(0, i, j)) {
                        // Malt eine Münze, die übermalt wird, wenn ein TheChosenOne gemalt wird
                        if (showCoins && !visited[i][j]) {
                            g2D.setPaint(Color.yellow);
                            g2D.fillOval((i + 1) * ZELLEN_GRÖßE + 10, (j + 1) * ZELLEN_GRÖßE + 10, 10, 10);
                        }

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
            if (opponent.getVisible()) {
                if (pacman.getSuperMode()) {
                    g2D.setPaint(Color.pink);
                }
                else {
                    g2D.setPaint(Color.red);
                }
                g2D.fillOval(opponent.getX() * ZELLEN_GRÖßE + 3, opponent.getY() * ZELLEN_GRÖßE + 3, 24, 24);
            }

        } else {
            if (!gameJustStartet) {
                // Game Over Screen

                g2D.setPaint(Color.red);
                g2D.setFont(new Font("MV Boli", Font.BOLD, 100));

                g2D.drawString("GAME OVER!", 130, 270);

                g2D.setFont(new Font("Calibri", Font.PLAIN, 30));
                g2D.setPaint(Color.yellow);

                g2D.drawString("Du hast " + punkte + " Punkte erreicht", 200, 550);
                g2D.drawString("Dein Highscore war " + highscore.getValue() + " Punkte", 200, 600);
            }
            else {
                // Starting Screen

                // Labyrinth im Hintergrund

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
                            if (showCoins) {
                                g2D.setPaint(Color.yellow);
                                g2D.fillOval((i + 1) * ZELLEN_GRÖßE + 10, (j + 1) * ZELLEN_GRÖßE + 10, 10, 10);
                            }

                        } else if (feld.isFieldWall(0, i, j)) {
                            // Malt die Wände
                            g2D.setPaint(Color.BLUE);
                            g2D.fillRect((i + 1) * ZELLEN_GRÖßE, (j + 1) * ZELLEN_GRÖßE, ZELLEN_GRÖßE, ZELLEN_GRÖßE);

                        } else if (feld.isFieldTheChosenOne(0, i, j)) {
                            // Malt eine Münze, die übermalt wird, wenn ein TheChosenOne gemalt wird
                            if (showCoins && !visited[i][j]) {
                                g2D.setPaint(Color.yellow);
                                g2D.fillOval((i + 1) * ZELLEN_GRÖßE + 10, (j + 1) * ZELLEN_GRÖßE + 10, 10, 10);
                            }

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

                // Eigentlicher Startbildschirm

                g2D.setPaint(new Color(56, 255, 0));
                g2D.setFont(new Font("Calibri", Font.BOLD, 75));

                g2D.drawString("Press S/R to start the Game!", 15, 200);

                g2D.setPaint(Color.yellow);

                g2D.fillArc(150, 350, 300, 300, 45, 270);

                g2D.fillOval(500, 482, 35, 35);
                g2D.fillOval(650, 482, 35, 35);
                g2D.fillOval(800, 482, 35, 35);

                g2D.setPaint(new Color(56, 255, 0));
                g2D.drawString("Dein Highscore war: " + highscore.getValue(), 20, 850);
            }
        }

    } // Malt

    // Im Prinzip Main Loop, der die Logik ausführt und dann nach jeder iteration die paint methode aufruft
    @Override
    public void actionPerformed(ActionEvent e) {

        // ANDERES WICHTIG

        // Nachschauen, ob es noch theChosenOnes gibt, um eine ArrayIndexOutOfBound Exception zu vermeiden
        if (subLevel == maze.getTheChosenOnesCounter()) {
            noMoreChosenOnes = true;
        }

        // Gegner flüchtet aus dem Labyrinth
        if(opponent.getMovesDid() < 1 && pacman.getSuperMode()) {
            gameOver();
        }

        // ENDE ANDERES WICHTIG

        // PACMAN

        // Pacman läuft nicht in Wände
        if (controllingEnabled) {
            try {
                if (feld.isFieldWall(pacman.getAngle(), pacman.getX() - 1, pacman.getY() - 1)) {
                    move = false;
                }
            } catch (ArrayIndexOutOfBoundsException exception) {
                move = false;
            }
        }

        // Pacman bewegen
        if (frameCount % pacman.getSpeed() == 0 && move) {
            switch (pacman.getAngle()) {
                case (1) -> pacman.moveUp(1);
                case (2) -> pacman.moveRight(1);
                case (3) -> pacman.moveDown(1);
                case (4) -> pacman.moveLeft(1);
            }
        }

        // Mund von Pacman auf und zu machen
        if (frameCount % 6 == 0) {
            auf = false;
        } else if (frameCount % 3 == 0) {
            auf = true;
        }

        // auf the chosen ones reagieren
        if (!noMoreChosenOnes) {
            if (maze.getTheChosenOneX(subLevel) == pacman.getX() - 1 && maze.getTheChosenOneY(subLevel) == pacman.getY() - 1) {
                feld.clearField(0, pacman.getX() - 1, pacman.getY() - 1);
                //System.out.println("A ChosenOne was eaten by Pacman"); zur Überprüfung der Funktion

                pacman.setSuperMode(true);
                showCoins = false;
                soundeffekte.playPowerUpSound();
            }
        }

        // Münzen fressen
        if (controllingEnabled && showCoins) {
            if (pacman.getX() > 0 && pacman.getX() < 31 && pacman.getY() > 0 && pacman.getX() < 31) {
                if (feld.isFieldCoin(0, pacman.getX() - 1, pacman.getY() - 1)) {
                    feld.clearField(0, pacman.getX() - 1, pacman.getY() - 1);
                    punkte++;
                }
            }
        }

        // ENDE PACMAN

        // GEGNER

        // Wird Gegner angezeigt und beginnt sich zu bewegen
        if (pacman.getMovesDid() > 3) {
            opponent.setVisible(true);
        }

        // Gegner wird bewegt
        if (opponent.getVisible() && opponentMove) {
            if (frameCount % opponent.getSpeed() == 0) {
                try {
                    opponent.goTo(pacman.getMovesX(opponent.getMovesDid()), pacman.getMovesY(opponent.getMovesDid()), pacman.getSuperMode());
                    //System.out.println("Opponent moved to: X: " + pacman.getMovesX(opponent.getMovesDid()) + " Y: " + pacman.getMovesY(opponent.getMovesDid()));
                } catch (Exception exception) {
                    System.out.println("Error");
                    opponent.goTo(pacman.getX(), pacman.getY(), pacman.getSuperMode());
                }
            }
        }

        // War Gegner schon bei TheChosenOne
        if (!noMoreChosenOnes) {
            if (opponent.getX() == maze.getTheChosenOneX(subLevel) + 1 && opponent.getY() == maze.getTheChosenOneY(subLevel) + 1) {
                opponentWasAlreadyAtTheChosenOne = true;
                //System.out.println("Gegner ist bei TheChosenOne"); zur Überprüfung der Funktion
            }
        }

        // Gegner tötet oder wird gefressen
        if (controllingEnabled) {
            if (opponent.getX() == pacman.getX() && opponent.getY() == pacman.getY() && opponentMove && opponent.getVisible()) {
                if (!pacman.getSuperMode()) {
                    // Gegner frisst.
                    // aktionen um den GameOver screen zu erzeugen
                    gameOver();
                } else {
                    // Pacman frisst.
                    if (!noMoreChosenOnes) {
                        timer.stop();
                        pacman.setSuperMode(false);

                        while (!(opponent.getX() == maze.getTheChosenOneX(subLevel) + 1 && opponent.getY() == maze.getTheChosenOneY(subLevel) + 1)) {

                            if (pacman.getLengthMovesX() - 1 >= opponent.getMovesDid() || pacman.getLengthMovesY() - 1 >= opponent.getMovesDid()) {
                                opponent.goTo(pacman.getMovesX(opponent.getMovesDid()), pacman.getMovesY(opponent.getMovesDid()), pacman.getSuperMode());
                            } else {
                                opponentWasAlreadyAtTheChosenOne = true;
                            }

                            if (opponentWasAlreadyAtTheChosenOne) {
                                opponent.setMovesDid(opponent.getMovesDid() - 2);
                            }
                            //System.out.println("Iteration"); zur Überprüfung der Funktion

                        }
                        // System.out.println("Sollte jetzt eigentlich beim TheChosenOne sein der gerade von Pacman gefressen wurde"); zur Überprüfung der Funktion

                        if (!noMoreChosenOnes) {
                            showCoins = true;
                        }

                        subLevel++;
                        opponentWasAlreadyAtTheChosenOne = false;
                        timer.start();
                    }
                }
            }
        }

        // Gegner lässt keinen zu großen Vorsprung zu
        distance = pacman.getMovesDid()-opponent.movesDid;

        if (!pacman.getSuperMode()) {
            if (distance < 55) {
                opponent.setSpeed((int) (11 - (distance / 5)));
            } else {
                opponent.setSpeed(1);
            }
        }
        else {
            opponent.setSpeed(13);
        }

        // ENDE GEGNER

        // ANDERES NICHT SO WICHTIG

        // Alle ChosenOnes wurden gefressen
        if (noMoreChosenOnes) {
            opponent.setVisible(false);
            opponentMove = false;

            canGoToNextLevel = true;
        }

        // Ins nächste Level gehen
        if (canGoToNextLevel) {
            if (pacman.getX() == 1 && pacman.getY() == 9 && pacman.getAngle() == 4) {
                controllingEnabled = false;
                //System.out.println("Controls disabled"); //zur überprüfung der Funktionsweise
            }
            if (pacman.getX() == -1 && pacman.getY() == 9) {
                level++;
                reset();
            }
            if (pacman.getX() == 1 && pacman.getY() == 9 && pacman.getAngle() == 2) {
                controllingEnabled = true;
                canGoToNextLevel = false;
                //System.out.println("Controls enabled"); //zur überprüfung der Funktionsweise
            }
        }

        // Markieren wo schon gewesen
        if (controllingEnabled) {
            if (pacman.getX() > 0 && pacman.getX() < 31 && pacman.getY() > 0 && pacman.getY() < 31) {
                visited[pacman.getX() - 1][pacman.getY() - 1] = true;
            }
        }

        // Highscore
        if (highscore.getValue() < punkte) {
            highscore.setValue(punkte);
        }

        // Music stoppen
        if (gameOver) {
            music.stop();
        }

        frameCount++;
        repaint();
    }

    public void gameOver() {
        gameOver = true;
        move = false;
        showCoins = false;
        opponentMove = false;
        controllingEnabled = false;

        //Soundeffekt
        soundeffekte.playGameOverSound();
    }

    public void reset() {
        timer.restart();

        feld = new Feld(LABYRINTH_GRÖßE);
        pacman = new Pacman(0, 9);
        opponent = new Opponent(0, 9, false);

        pacman.setAngle(2);
        pacman.setMovesX(0, 0);
        pacman.setMovesY(0, 9);
        pacman.setSpeed(9);

        music.startFromBeginning();

        maze.generate();
        maze.shuffleTheChosenOnes(100);

        frameCount = 0;
        subLevel = 0;

        auf = false;
        move = true;
        opponentMove = true;
        noMoreChosenOnes = false;
        opponentWasAlreadyAtTheChosenOne = false;
        gameOver = false;
        controllingEnabled = true;
        showCoins = true;
        gameJustStartet = false;

        //System.out.println("The Programm has been resettet"); zur Überprüfung der Funktionsweise
    }

    public void resetPointCounter() {
        punkte = 0;
    }

    // Getter Methoden
    public boolean isControllingEnabled() {
        return controllingEnabled;
    }
    public boolean isGameOver() {
        return gameOver;
    }

    // Setter Methoden
    public void setPacmanAngle(int pacmanAngle) {
        pacman.setAngle(pacmanAngle);
    }
    public void setMove(boolean newMoveValue) {
        move = newMoveValue;
    }
}