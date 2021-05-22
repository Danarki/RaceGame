package RaceGame;
import java.awt.*;
import java.awt.event.*;

public class Car extends KeyAdapter {
    private final Component component;
    private final int deltaX;
    private final int deltaY;

    public Car(Component component, int deltaX, int deltaY) {
        this.component = component;
        this.deltaX = deltaX;
        this.deltaY = deltaY;

    }
}