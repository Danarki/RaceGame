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
    public static JLabel time;
    public static JMenuBar topbar;
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
            BufferedImage heartImg = ImageIO.read(new File("src/RaceGame/images/heart.png"));
            JLabel heart = new JLabel(new ImageIcon(heartImg));
            heart.setBounds(2, 2, 60, 51);
            mb.add(heart);

            mb.add(menu);
            frame.setJMenuBar(mb);
            frame.setSize(400, 400);
            frame.setLayout(null);
            frame.setVisible(true);
        }
            catch (Exception e) {
            System.out.println("Error!");
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        Test window = new Test();
    }
}

