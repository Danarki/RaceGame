package RaceGame;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Timer extends Thread {
    public static Date start;
    public boolean running;
    public static int score;

    @Override
    public void run() {
        score = 0;
        start = new Date();
        running = true;
        while(running){
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

            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
            String timeNow = sdf.format(new Date((now.getTime()) - start.getTime()));
            Window.setTime(timeNow);

            sdf = new SimpleDateFormat("ss");
            timeNow = sdf.format(new Date((now.getTime() - 1000) - start.getTime()));
            int seconds1 = Integer.parseInt(timeNow);
            sdf = new SimpleDateFormat("mm");
            timeNow = sdf.format(new Date((now.getTime() - 1000) - start.getTime()));
            int seconds2 = Integer.parseInt(timeNow) * 60;

            int totalSeconds = seconds1 + seconds2;

            score = totalSeconds * 100;

            if (!Window.checkAlive()){
                running = false;
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static int getScore() {
        return score;
    }
}