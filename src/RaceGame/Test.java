package RaceGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test extends Thread {


    public static JFrame frame;
    public JPanel content;
    public static JMenuBar topbar;
    public static JMenu lives;
    public static JMenu time;
    public Date start = new Date();

    public Test() {
        content = new JPanel();

        try {

            frame = new JFrame("Super coole race game :O");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JMenu menu, submenu;
            JMenuItem i1, i2, i3, i4, i5;

            JMenuBar mb = new JMenuBar();
            mb.setSize(600, 200);
            menu = new JMenu("Menu");

            time = new JMenu("time: 00:00");
            mb.add(time);

            BufferedImage heartImg = ImageIO.read(new File("src/RaceGame/images/heart.png"));
            JLabel heart = new JLabel(new ImageIcon(heartImg));
            heart.setBounds(2, 2, 60, 51);
            mb.add(heart);

            lives = new JMenu("x3");
            mb.add(lives);

            mb.add(menu);
            frame.setJMenuBar(mb);
            frame.setSize(600, 800);
            frame.setLocationByPlatform(true);
            frame.setResizable(false);

            frame.setVisible(true);
        }
            catch (Exception e) {
            System.out.println("Error!");
            System.out.println(e);
        }
    }

    public static void main(String[] args) {

        Test window = new Test();

        try {
            Thread.sleep(3000);
            lives.setText("x2");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

