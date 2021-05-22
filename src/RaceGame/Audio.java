package RaceGame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Audio {
    public static Clip gameoverClip;
    public static Clip titleClip;
    public static Clip playingClip;
    public static Clip explodeClip;

    // Muziek tijdens het spelen van de game
    public static void playing(){
        int rand = (int) Math.floor(Math.random() * 5) + 1;

        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/RaceGame/sounds/playing" + rand + ".wav"));
            playingClip = AudioSystem.getClip();
            playingClip.open(audioInputStream);
            playingClip.start();

        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
    }

    // Muziek wanneer je je in de "Title Screen" bevindt
    public static void title(){
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/RaceGame/sounds/title.wav"));
            titleClip = AudioSystem.getClip();
            titleClip.open(audioInputStream);
            titleClip.start();
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
    }

    // Effect terwijl je met een Obstacle bent gebotst
    public static void explode(){
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/RaceGame/sounds/explosion.wav"));
            explodeClip = AudioSystem.getClip();
            explodeClip.open(audioInputStream);
            explodeClip.start();
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
    }

    // Muziek terwijl je "Game Over" bent
    public static void gameover(){
        playingStop();
        explodeClip.stop();
        Thread thread1 = new Thread(() -> {
            try{
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/RaceGame/sounds/gameover.wav"));
                gameoverClip = AudioSystem.getClip();
                gameoverClip.open(audioInputStream);
                gameoverClip.start();
            }
            catch(Exception ex)
            {
                System.out.println(ex);
            }
        });
        thread1.start();
    }

    // Het stoppen van de muziek binnen bepaalde schermen
    public static void gameoverStop(){
        gameoverClip.stop();
    }

    public static void titleStop(){
        titleClip.stop();
    }

    public static void playingStop(){
        playingClip.stop();
    }
}
