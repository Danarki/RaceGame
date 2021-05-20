// TODO: make screen go to main menu || add leaderboard refresh on restart
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
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

public class Window extends Thread {
    private static JFrame frame;
    private static JLabel car;
    private static int first = 0;
    private static JMenu lives;
    private static int lifeCount = 3;
    private static JMenu time;
    private static JPanel menuPanel;
    private static int pause;
    private static boolean alive;
    private static JPanel homePanel;
    private static JPanel endPanel;
    private static JMenuBar menuBar;
    private static boolean invincibleState = false;
    private static boolean windowRun = true;
    private static int initialisedEndScreen = 0;
    private static int ended = 0;
    private static JLabel endScore;
    private static JTextField nameField;
    private static final Thread game = new Thread() {
        public void run() {
            try {
                menuBar = new JMenuBar();
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

                JMenu menu = new JMenu("Menu");
                menu.addMenuListener(new MenuListener() {
                    @Override
                    public void menuSelected(MenuEvent e) {
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

                KeyboardFocusManager.getCurrentKeyboardFocusManager()
                        .addKeyEventDispatcher(new KeyEventDispatcher() {
                            @Override
                            public boolean dispatchKeyEvent(KeyEvent e) {
                                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                                    move(-10, 0, pause);

                                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                                    move(10, 0, pause);

                                }
                                return false;
                            }
                        });


                frame.add(car);
                car.setBounds(250, 460, image.getWidth(), image.getHeight());

                menuPanel = new JPanel();
                menuPanel.setBounds(200, 0, 400, 200);
                menuPanel.setBackground(Color.WHITE);
                Border blackLine = BorderFactory.createLineBorder(Color.black);
                menuPanel.setBorder(blackLine);
                menuPanel.setVisible(false);
                ResultSet rs = SQL.select();
                int count = 1;
                while (true) {
                    assert rs != null;
                    if (!rs.next()) break;
                    if (count < 6) {
                        String name = rs.getString(2);
                        String score = String.valueOf(rs.getInt(3));
                        String date = String.valueOf(rs.getDate(4));
                        JLabel plate = new JLabel(count + ". " + name + " | " + score + " | " + date);
                        menuPanel.add(plate);
                        plate.setBounds(10, count * 20, 400, 100);
                        count++;
                    }
                }

                menuPanel.setLayout(null);
                frame.add(menuPanel);

                JButton a = new JButton("X");
                a.setBounds(130, 10, 45, 30);
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
            } catch (IOException | SQLException e) {
                System.out.println("Error!");
                System.out.println(e);
            }

            alive = true;

            //fixes the topbar
            lives.setText("x3 ");
            car.repaint();

            Window s = new Window();
            s.start();
        }
    };

    public static void startGame() {
        //start = new Date();
        game.start();
    }

    public static void endGame() {
        if (ended == 0 && initialisedEndScreen == 0) {
            car.setVisible(false);
            menuBar.setVisible(false);

            int score = Timer.getScore();
            String name = String.valueOf(nameField.getText());
            SQL.insert(name, score);

            Border blackLine = BorderFactory.createLineBorder(Color.black);

            endPanel = new JPanel();
            endPanel.setBorder(blackLine);
            endPanel.setLocation(100, 50);
            endPanel.setSize(400, 350);
            endPanel.setBackground(Color.WHITE);
            endPanel.setLayout(null);
            frame.add(endPanel);

            JLabel endTitle = new JLabel("You crashed!");
            endTitle.setFont(new Font("Calibri", Font.BOLD, 46));
            endPanel.add(endTitle);
            endTitle.setBounds(75, 20, 400, 50);

            endScore = new JLabel("You scored: " + score + "!");
            endScore.setSize(400, 100);
            endScore.setLocation(110, 100);
            endScore.setFont(new Font("Calibri", Font.BOLD, 24));
            endPanel.add(endScore);

            JButton restartButton = new JButton("Play again");
            restartButton.setBounds(25, 225, 175, 100);
            restartButton.setFont(new Font("Calibri", Font.BOLD, 24));
            endPanel.add(restartButton);
            restartButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (nameField.getText().equals("")) {
                        JOptionPane.showMessageDialog(new JFrame(), "You have to enter your name first!", "Error!",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    lifeCount = 3;
                    lives.setText("x3");
                    windowRun = true;
                    alive = true;
                    car.setVisible(true);
                    menuBar.setVisible(true);
                    Window s = new Window();
                    s.start();
                    try {

                        Thread.sleep(500);

                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    endPanel.setVisible(false);
                }
            });

            JButton menuButton = new JButton("Main menu");
            menuButton.setBounds(210, 225, 175, 100);
            menuButton.setFont(new Font("Calibri", Font.BOLD, 24));
            endPanel.add(menuButton);

            if (ended == 0) {
                ended = 1;
            }
            windowRun = false;
            initialisedEndScreen = 1;
        } else {
            int score = Timer.getScore();
            String name = String.valueOf(nameField.getText());
            SQL.insert(name, score);
            endScore.setText("You scored: " + score + "!");
            car.setVisible(false);
            menuBar.setVisible(false);
            endPanel.setVisible(true);
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


    public static void init() {
        frame = new JFrame("Super coole race game :O");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUIFont(new FontUIResource("Calibri", Font.BOLD, 16));
        frame.setSize(600, 800);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);
        frame.setLayout(null);

        homePanel = new JPanel();
        homePanel.setSize(600, 800);
        homePanel.setLayout(null);
        frame.add(homePanel);

        JLabel nameLabel = new JLabel("Enter your name:");
        homePanel.add(nameLabel);
        nameLabel.setBounds(240, 75, 200, 25);

        nameField = new JTextField("");
        homePanel.add(nameField);
        nameField.setBounds(200, 100, 200, 25);

        JButton startButton = new JButton("Start");
        startButton.setBounds(200, 200, 200, 100);
        startButton.setFont(new FontUIResource("Calibri", Font.BOLD, 32));

        homePanel.add(startButton);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameField.getText().equals("")) {
                    JOptionPane.showMessageDialog(new JFrame(), "You have to enter your name first!", "Error!",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                ended = 0;
                startGame();
                try {
                    Thread.sleep(500);

                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                homePanel.setVisible(false);

            }
        });


        frame.setVisible(true);
    }


    public static boolean checkAlive() {
        return alive;
    }

    // zorgt ervoor dat de tijd wordt bijgehouden en dat deze update
    @Override
    public void run() {
        Timer t = new Timer();
        t.start();
        while (windowRun) {
            while (alive) {
                if (checkAlive()) {
                    if (getPause() == 0) {
                        try {
                            GenerateObstacle g = new GenerateObstacle();
                            BufferedImage component = g.generate();
                            Window.addNewComponent(new ImageIcon(component));
                            //Thread.sleep(500);
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                }
            }
        }
        if (ended == 0) {
            endGame();
        }
    }

    public static void setTime(String t) {
        if (checkAlive())
            try {
                time.setText("time: " + t);
            } catch (Exception e) {
                System.out.println(e);
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

    public static void addNewComponent(ImageIcon icon) {
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

    public static boolean getInvincibleState() {
        return invincibleState;
    }

    public static void invincibile() {
        Thread invincible = new Thread() {
            public void run() {
                try {
                    invincibleState = true;
                    car.setVisible(false);
                    Thread.sleep(250);
                    car.setVisible(true);
                    Thread.sleep(250);
                    car.setVisible(false);
                    Thread.sleep(250);
                    car.setVisible(true);
                    Thread.sleep(250);
                    car.setVisible(false);
                    Thread.sleep(250);
                    car.setVisible(true);
                    Thread.sleep(250);
                    car.setVisible(false);
                    Thread.sleep(250);
                    car.setVisible(true);
                    Thread.sleep(250);
                    invincibleState = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        if (lifeCount > 0) {
            invincible.start();

        }
    }

    public static void depleteLives() {
        lifeCount--;

        if (lifeCount < 1) {
            alive = false;
            lives.setText("x0");
        } else {
            lives.setText("x" + lifeCount);
        }
    }
}
