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
    public Date start = new Date();

    @Override
    public void run() {
        boolean a = true;
        while(a){
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
            String timeNow = sdf.format(new Date((now.getTime() - 1000) - start.getTime()));
            System.out.println(timeNow);
            if (timeNow.equals("00:05")){
                a = false;
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }



    public static void main(String[] args) {
        Test t = new Test();
        t.start();
    }

}