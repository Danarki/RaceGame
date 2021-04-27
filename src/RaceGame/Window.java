package RaceGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Window {
    public JFrame frame;
    public JPanel content;
    public static JLabel car;
    public int first = 0;

    public Window() {
        content = new JPanel();

        try {
            frame = new JFrame("Super coole race game :O");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            BufferedImage image = ImageIO.read(new File("src/RaceGame/images/car.png"));
            car = new JLabel(new ImageIcon(image));
            JButton left = new JButton();

            left.setOpaque(false);
            left.setContentAreaFilled(false);
            left.setBorderPainted(false);
            frame.add(car);
            car.setBounds(250, 500, 100, 244);
            frame.add(left);
            left.setBounds(0, 0, 100, 100);
            left.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        move(-10, 0);


                    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        move(10, 0);


                    }
                }
            });
            frame.setSize(600, 800);
            frame.setLocationByPlatform(true);
            frame.setResizable(false);

            frame.setVisible(true);
        } catch (IOException e) {
            System.out.println("Error!");
            System.out.println(e);
        }
    }

    public static void move(int deltaX, int deltaY) {
        int componentWidth = car.getSize().width;
        int componentHeight = car.getSize().height;

        Dimension parentSize = car.getParent().getSize();
        int parentWidth = parentSize.width;
        int parentHeight = parentSize.height;

        //zorgt ervoor dat de auto niet buiten het scherm kan bewegen
        int nextX = Math.max(car.getLocation().x + deltaX, 0);

        if (nextX + componentWidth > parentWidth) {
            nextX = parentWidth - componentWidth;
        }


        int nextY = Math.max(car.getLocation().y + deltaY, 0);

        if (nextY + componentHeight > parentHeight) {
            nextY = parentHeight - componentHeight;
        }


        car.setLocation(nextX, nextY);
    }

    public void addNewComponent(ImageIcon icon) {
        int x = (int) Math.floor(Math.random() * (600 + 1));
        int y = 0;
        if (first == 0){
            first++;
        } else {
            JLabel label = new JLabel(icon);
            frame.add(label);
            int componentHeight = icon.getIconHeight();
            y = y - componentHeight;
            label.setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
            GenerateObstacle.addObstacleMovement(label, x, y, car);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
