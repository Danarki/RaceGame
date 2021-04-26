package RaceGame;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Obstacle {
    BufferedImage image;

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
