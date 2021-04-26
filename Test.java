package RaceGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Test extends Thread{
    public void run()
    {
        try {
            // Displaying the thread that is running
            System.out.println(
                    "Thread " + Thread.currentThread().getId()
                            + " is running");
        }
        catch (Exception e) {
            // Throwing an exception
            System.out.println("Exception is caught");
        }
    }
    public static void main(String[] arg)  {
        int n = 8; // Number of threads
        for (int i = 0; i < n; i++) {
            Test object
                    = new Test();
            object.start();
        }
        }
    }

