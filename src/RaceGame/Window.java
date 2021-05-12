package RaceGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.plaf.FontUIResource;
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
    private static JFrame frame;
    private static JLabel car;
    private int first = 0;
    private int firstTime = 0;
    private static JMenu lives;
    private static int lifeCount = 3;
    private JMenu time;
    private JPanel menuPanel;
    private JMenu menu;
    private final Date start = new Date();
    private static int pause;
    private static boolean alive;


    public Window() {
        //initialisatie van het scherm
        try {

            frame = new JFrame("Super coole race game :O");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setUIFont(new FontUIResource("Calibri", Font.BOLD, 16));


            JMenuBar menuBar = new JMenuBar();
            menuBar.setSize(600, 200);


            time = new JMenu("time: 00:00");
            menuBar.add(time);
            time.setFocusable(false);
            menuBar.add(Box.createHorizontalGlue());

            BufferedImage heartImg = ImageIO.read(new File("src/RaceGame/images/heart.png"));
            JLabel heart = new JLabel(new ImageIcon(heartImg));
            heart.setBounds(2, 2, 60, 51);
            menuBar.add(heart);
            heart.setFocusable(false);

            lives = new JMenu("x3");
            menuBar.add(lives);
            lives.setFocusable(false);

            menu = new JMenu("Menu");
            menu.addMenuListener(new MenuListener() {
                @Override
                public void menuSelected(MenuEvent e) {
                    System.out.println("c");
                    if (getPause() == 0) {
                        pause++;
                        menuPanel.setVisible(true);
                        menuPanel.requestFocusInWindow();

                    } else if (getPause() == 1) {
                        pause--;
                        menuPanel.setVisible(false);

                    }

                }

                @Override
                public void menuDeselected(MenuEvent e) {
                }

                @Override
                public void menuCanceled(MenuEvent e) {

                }


            });

            menuBar.add(Box.createHorizontalGlue());
            menuBar.add(menu);

            frame.setJMenuBar(menuBar);

            BufferedImage image = ImageIO.read(new File("src/RaceGame/images/car.png"));
            car = new JLabel(new ImageIcon(image));
            JButton controls = new JButton();

            controls.setOpaque(false);
            controls.setContentAreaFilled(false);
            controls.setBorderPainted(false);
            frame.add(controls);

            controls.setBounds(0, 0, 100, 100);
            controls.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        move(-10, 0, pause);

                    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        move(10, 0, pause);

                    }
                }
            });

            frame.add(car);
            car.setBounds(250, 460, image.getWidth(), image.getHeight());

            menuPanel = new JPanel();
            menuPanel.setBounds(400,0,200,200);
            menuPanel.setBackground(Color.GREEN);
            Border blackline = BorderFactory.createLineBorder(Color.black);
            menuPanel.setBorder(blackline);
            menuPanel.setVisible(false);
            menuPanel.setLayout(null);
            frame.add(menuPanel);

            JButton a = new JButton("X");
            a.setBounds(130,10,45,30);
            menuPanel.add(a);
            a.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (getPause() == 0) {
                        pause++;
                        menuPanel.setVisible(true);
                        menuPanel.requestFocusInWindow();

                    } else if (getPause() == 1) {
                        pause--;
                        menuPanel.setVisible(false);

                    }
                }
            });

            frame.setSize(600, 800);
            frame.setLocationByPlatform(true);
            frame.setResizable(false);
            frame.setLayout(null);
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

    public static void depleteLives(){
        lifeCount--;

        if (lifeCount < 1){
            alive = false;
            lives.setText("x0");
            System.out.println("dead");
        } else {
            lives.setText("x" + lifeCount);
        }
    }

    public static void startGame(){
        Window scherm = new Window();
        // Start de timer
        scherm.start();
        alive = true;
        while (true) {
            if (checkAlive()){
                if (Window.getPause() == 0) {


                    try {
                        GenerateObstacle g = new GenerateObstacle();
                        BufferedImage component = g.generate();
                        scherm.addNewComponent(new ImageIcon(component));

                        if (!checkAlive()){
                            endGame();
                        }


                        Thread.sleep(1000);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            } else {
                endGame();
            }

        }
    }

    public static void endGame(){
        car.setVisible(false);
    }


    public static boolean checkAlive() {
        return alive;
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
                String timeNow = sdf.format(new Date((now.getTime() - 1000) - start.getTime()));
                setTime(timeNow);
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    //Deze methode i.p.v setText(), omdat revalidate() ervoor zorgt dat de obstacles niet goed meer werken.

    public void setTime(String t) {
        if (checkAlive())
        if (firstTime > 0) {
            try {
                time.setText("time: " + t);
            } catch (Exception e) {
                System.out.println(e);
            }
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
