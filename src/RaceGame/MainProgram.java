package RaceGame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainProgram {
    public static void main(String[] args) throws InterruptedException {
        Window scherm = new Window();

        while (true){
            try {
                GenerateObstacle g = new GenerateObstacle();
                BufferedImage component = g.generate();
                scherm.addNewComponent(new ImageIcon(component));
                Thread.sleep(1000);
            } catch (Exception e){
                System.out.println(e);
            }

        }
    }
}
