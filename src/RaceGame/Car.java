package RaceGame;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Car extends KeyAdapter {
    private Component component;
    private int deltaX;
    private int deltaY;

    public Car(Component component, int deltaX, int deltaY) {
        this.component = component;
        this.deltaX = deltaX;
        this.deltaY = deltaY;

    }
}