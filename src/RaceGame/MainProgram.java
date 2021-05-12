package RaceGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainProgram {
    public static void main(String[] args) throws InterruptedException {
        Window scherm = new Window();
        // Start de timer
        scherm.start();

        while (true) {
            if (Window.getPause() == 0) {


                try {
                    GenerateObstacle g = new GenerateObstacle();
                    BufferedImage component = g.generate();
                    scherm.addNewComponent(new ImageIcon(component));


                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else {
                Thread.sleep(1000);
            }
        }
    }
}
