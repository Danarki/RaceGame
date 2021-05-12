package RaceGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

public class Window extends Thread {
    public JFrame frame;
    public JPanel content;
    public static JLabel car;
    public int first = 0;
    public int firstTime = 0;
    public JLabel livesLabel;
    public int lives;
    public JLabel time;
    public JPanel topbar;
    public JPanel menuPanel;
    public Date start = new Date();
    public String timeNow;
    public JButton menu;
    public static int pause;

    public Window() {
        content = new JPanel();
        //initialisatie van het scherm
        try {

            frame = new JFrame("Super coole race game :O");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setUIFont(new javax.swing.plaf.FontUIResource("Calibri", Font.BOLD, 16));

            topbar = new JPanel();
            topbar.setBounds(-1, -1, 600, 100);
            Border b = BorderFactory.createLineBorder(Color.BLACK, 1);
            topbar.setBorder(b);
            topbar.setBackground(Color.WHITE);
            topbar.setOpaque(true);
            topbar.setLayout(new GridLayout(1, 6));

            frame.add(topbar);
            start = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
            time = new JLabel("");
            topbar.add(time);

            BufferedImage image = ImageIO.read(new File("src/RaceGame/images/car.png"));
            car = new JLabel(new ImageIcon(image));
            JButton left = new JButton();

            BufferedImage heartImg = ImageIO.read(new File("src/RaceGame/images/heart.png"));
            JLabel heart = new JLabel(new ImageIcon(heartImg));
            heart.setBounds(2, 2, 60, 51);
            topbar.add(heart);

            lives = 3;
            livesLabel = new JLabel("x " + lives);
            topbar.add(livesLabel);
            livesLabel.setLocation(-50, 5);

            JLabel divider = new JLabel("");
            topbar.add(divider);

            menu = new JButton("Menu");
            topbar.add(menu);
            menu.setFocusable(false);
            menuPanel = new JPanel();
            menuPanel.setSize(200, 200);
            menuPanel.setLocation(390, 98);
            menuPanel.setBackground(Color.WHITE);
            menuPanel.setBorder(b);
            menuPanel.setVisible(false);
            frame.add(menuPanel);

            //frame.setComponentZOrder(menuPanel, 1);
            menu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (getPause() == 0) {
                        pause++;
                        menuPanel.setVisible(true);
                        frame.repaint();
                    } else if (getPause() == 1) {
                        pause--;
                        menuPanel.setVisible(false);
                        frame.repaint();
                    }

                }
            });

            topbar.add(divider);

            left.setOpaque(false);
            left.setContentAreaFilled(false);
            left.setBorderPainted(false);

            frame.add(car);
            car.setBounds(250, 520, image.getWidth(), image.getHeight());

            frame.add(left);
            left.setBounds(0, 0, 100, 100);
            left.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        move(-10, 0, pause);


                    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        move(10, 0, pause);


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

    public static int getPause() {
        return pause;
    }

    public static void setUIFont(javax.swing.plaf.FontUIResource f) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put(key, f);
        }
    }

    // zorgt ervoor dat de tijd wordt bijgehouden en dat deze update
    @Override
    public void run() {
        while (true) {
            int pauseState = Window.getPause();
            while (pauseState == 1) {
                try {
                    start.setTime(start.getTime() + 100);
                    Thread.sleep(100);
                    pauseState = Window.getPause();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Date now = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
                timeNow = sdf.format(new Date((now.getTime() - 1000) - start.getTime()));
                setTime(timeNow);
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    //Deze methode i.p.v setText(), omdat revalidate() ervoor zorgt dat de obstacles niet goed meer werken.

    public void setTime(String t) {
        System.out.println(t);
        if (firstTime > 0) {
            topbar.remove(time);
            try {
                time = new JLabel("time: " + t);
                topbar.add(time);
                time.setBounds(25, 40, 100, 20);

            } catch (Exception e) {
                System.out.println(e);
            }
            topbar.repaint();
        } else {
            firstTime++;

        }


    }

    //Laat de auto bewegen

    public static void move(int deltaX, int deltaY, int pause) {
        if (pause == 0) {
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
    }

    public void addNewComponent(ImageIcon icon) {
        int x = (int) Math.floor(Math.random() * (600 + 1)) - 50;
        int y = 0;
        if (first == 0) {
            first++;
        } else {
            JLabel label = new JLabel(icon);
            frame.add(label);
            int componentHeight = icon.getIconHeight();
            y = y - componentHeight;
            label.setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
            GenerateObstacle.addObstacleMovement(label, x, car);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
