package RaceGame;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Obstacle {
    BufferedImage image;

    // Maakt een Obstacle aan die met de willekeurige meegegeven waarde het plaatje bepaald
    public Obstacle(int type){
        if (type == 1){
            try {
                this.image = ImageIO.read(new File("src/RaceGame/images/rock.png"));

            } catch (IOException e) {
                System.out.println(e);
            }

        } else if (type == 2){
            try {
                this.image = ImageIO.read(new File("src/RaceGame/images/log.png"));

            } catch (IOException e) {
                System.out.println(e);
            }
        } else {
            try {
                this.image = ImageIO.read(new File("src/RaceGame/images/chicken.png"));

            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
