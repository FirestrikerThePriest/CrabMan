import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Soundeffekte {
    Clip gameOverSoundClip;
    Clip powerUpSoundClip;

    public Soundeffekte() {
        File fileGameOverSound  = new File("C:\\Users\\lenna\\Documents\\Freizeit\\Java\\Dateien fuer Programme\\Programm Crabman\\Pacman Game Over Sound.wav");
        File filePowerUpSound = new File("C:\\Users\\lenna\\Documents\\Freizeit\\Java\\Dateien fuer Programme\\Programm Crabman\\Power Up Sound.wav");

        try {
            AudioInputStream audioStreamGameOverSound = AudioSystem.getAudioInputStream(fileGameOverSound);
            AudioInputStream audioStreamPowerUpSound = AudioSystem.getAudioInputStream(filePowerUpSound);

            gameOverSoundClip = AudioSystem.getClip();
            gameOverSoundClip.open(audioStreamGameOverSound);

            powerUpSoundClip = AudioSystem.getClip();
            powerUpSoundClip.open(audioStreamPowerUpSound);
        }
        catch (IOException e) {
            System.out.println("Es ist Etas schiefgegangen...");
            System.out.println("Softwarefehler kann man nichts machen...");
        }
        catch (UnsupportedAudioFileException e) {
            System.out.println("Such dir ne passende Datei du Blödkopf!");
        }
        catch (LineUnavailableException e) {
            System.out.println("Eine Zeile der Datei ist nicht lesbar. \n Bitte vergewissere ich das sie nicht beschädigt ist");
        }
    }

    public void stop() {
        gameOverSoundClip.stop();
        powerUpSoundClip.stop();
    }

    public void playGameOverSound() {
        gameOverSoundClip.setMicrosecondPosition(0);
        gameOverSoundClip.start();

        new Thread(() -> {
            try {
                Thread.sleep(5000);

                gameOverSoundClip.stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void playPowerUpSound() {
        powerUpSoundClip.setMicrosecondPosition(0);
        powerUpSoundClip.start();

        new Thread(() -> {
            try {
                Thread.sleep(5000);

                powerUpSoundClip.stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
