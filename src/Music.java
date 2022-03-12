import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Music {
    Clip clip;

    boolean musicRunning = false;
    boolean reactOnMute = true;

    Music() {
        File file  = new File("C:\\Users\\lenna\\Documents\\Freizeit\\Java\\Dateien fuer Programme\\Programm Crabman\\Crabman Music.wav");

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);

            clip = AudioSystem.getClip();
            clip.open(audioStream);
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
        clip.stop();

        musicRunning = false;
    }

    public void start() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);

        musicRunning = true;
    }

    public void startFromBeginning() {
        clip.setMicrosecondPosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);

        musicRunning = true;
    }

    public void setMusicRunning(boolean musicRunning) {
        this.musicRunning = musicRunning;
    }
    public void setReactOnMute(boolean reactOnMute) {
        this.reactOnMute = reactOnMute;
    }

    public boolean isMusicRunning() {
        return musicRunning;
    }
    public boolean isReactOnMute() {
        return reactOnMute;
    }
}
