package RaceGame;

import java.io.IOException;

public class MainProgram {
    public static void main(String[] args) throws InterruptedException, IOException {
        Window.init();
        Arduino.connect();
    }
}
