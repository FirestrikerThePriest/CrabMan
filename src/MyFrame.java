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
        this.setLocation(600, 100);

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
                case (38):
                case (87):
                    panel.setPacmanAngle(1);
                    break;
                case (39):
                case (68):
                    panel.setPacmanAngle(2);
                    break;
                case (40):
                case (83):
                    panel.setPacmanAngle(3);
                    break;
                case (37):
                case (65):
                    panel.setPacmanAngle(4);
                    break;
            }
        }
        else {
            panel.setPacmanAngle(3);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}