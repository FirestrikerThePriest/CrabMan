import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyFrame extends JFrame implements KeyListener {
    MyPanel panel;

    MyFrame() {
        this.setTitle("Pacman");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocation(600, 50);

        panel = new MyPanel();

        ImageIcon image = new ImageIcon("C:\\Users\\lenna\\Pictures\\Saved Pictures\\Andere\\PacmanIcon.png");
        this.setIconImage(image.getImage());

        this.add(panel); // NICHT ADDEN VERGESSEN HÄUFIGER FEHLER WENN DU DEN GEMACHT HAST GEH ZURÜCK UND FIXE IHN DU FRICASSEE
        this.addKeyListener(this);

        this.pack();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (panel.isControllingEnabled()) {
            panel.setMove(true);
            switch (e.getKeyCode()) {
                case (38), (87) -> panel.setPacmanAngle(1);
                case (39), (68) -> panel.setPacmanAngle(2);
                case (40), (83) -> panel.setPacmanAngle(3);
                case (37), (65) -> panel.setPacmanAngle(4);
                case (75), (88) -> panel.gameOver();
            }
        }
        else {
            panel.setPacmanAngle(3);

            switch (e.getKeyCode()) {
                case (82): // = 'r'
                case (83): // = 's'
                    if (panel.isGameOver()) {
                        panel.reset();
                        panel.resetPointCounter();
                    }
                    break;
                case (81):
                    System.exit(0);
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}