package RaceGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GenerateObstacle {

    // Maakt een van de 3 Obstacles aan
    public BufferedImage generate() {
        int rand = (int) Math.floor(Math.random() * 4);
        Obstacle o = new Obstacle(rand);
        return o.image;
    }


    // Voegt de beweging toe aan het aangemaakte Obstacle
    public static void addObstacleMovement(JLabel obstacle, int x, JLabel car) {
        if (Window.checkAlive()) {

            Thread thread1 = new Thread() {
                public void run() {
                    Dimension parentSize = car.getParent().getSize();
                    int parentHeight = parentSize.height;
                    int componentHeight = obstacle.getSize().height;
                    boolean looper = true;
                    while (looper) {
                        int pauseState = Window.getPause();
                        while (pauseState == 1) {
                            try {
                                Thread.sleep(100);
                                pauseState = Window.getPause();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        int nextY = Math.max(obstacle.getLocation().y + 10, 0);

                        if (nextY - componentHeight > parentHeight - componentHeight) {
                            nextY = parentHeight - componentHeight;
                            looper = false;
                            Container parent = obstacle.getParent();
                            parent.remove(obstacle);
                            parent.repaint();

                        }

                        obstacle.setLocation(x, nextY);

                        int carX = car.getX();
                        int carY = car.getY();
                        int carWidth = car.getWidth();
                        int carHeigth = car.getHeight();
                        int obstacleX = obstacle.getX();
                        int obstacleY = obstacle.getY();
                        int obstacleWidth = obstacle.getWidth();
                        int obstacleHeigth = obstacle.getHeight();

                        boolean detect = this.collisionDetect(carX, carY, carWidth, carHeigth, obstacleX, obstacleY, obstacleWidth, obstacleHeigth);

                        if (detect && !Window.getInvincibleState() || !Window.checkAlive()) {
                            looper = false;
                            Container parent = obstacle.getParent();
                            parent.remove(obstacle);
                            parent.repaint();
                            try {
                                BufferedImage explosion = ImageIO.read(new File("src/RaceGame/images/explosion.png"));
                                JLabel explode = new JLabel(new ImageIcon(explosion));
                                car.getParent().add(explode);
                                Audio.explode();
                                explode.setBounds(car.getX() - 20, car.getY() - 100, explosion.getWidth(), explosion.getHeight());
                                try {
                                    Window.depleteLives();
                                    Window.invincibile();
                                    Thread.sleep(1000);
                                    car.getParent().remove(explode);
                                    car.getParent().repaint();

                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                // Detecteerd of het huidige Obstacle botst met de auto
                private boolean collisionDetect(int carX, int carY, int carWidth, int carHeigth, int obstacleX, int obstacleY, int obstacleWidth, int obstacleHeight) {
                    return carX < obstacleX + obstacleWidth &&
                            carX + carWidth > obstacleX &&
                            carY < obstacleY + obstacleHeight &&
                            carY + carHeigth > obstacleY;
                }

            };
            thread1.start();
        }
    }
}
