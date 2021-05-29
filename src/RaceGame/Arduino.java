package RaceGame;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import java.io.*;


public class Arduino {
    public static void connect() throws IOException {
        SerialPort sp = SerialPort.getCommPort("COM4");
        sp.setComPortParameters(9600, 8, 1, 0);
        sp.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        sp.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);

        if(sp.openPort()) {
            System.out.println("port is opened");
        } else {
            System.out.println("port failed to open");
            return;
        }

        sp.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            public void serialEvent(SerialPortEvent event) {

                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                    return;

                BufferedReader portReader = new BufferedReader(new InputStreamReader(sp.getInputStream()));

                try {
                    String line = portReader.readLine();
                    System.out.println(line);

                    Robot Ardu = new Robot();
                    switch(line){
                        case "left" :
                            Ardu.keyPress(KeyEvent.VK_LEFT);//presses left key.
                            Ardu.keyRelease(KeyEvent.VK_LEFT);//releases left key
                            break;
                        case "right" :
                            Ardu.keyPress(KeyEvent.VK_RIGHT);
                            Ardu.keyRelease(KeyEvent.VK_RIGHT);
                            break;
                    }

                } catch (IOException | AWTException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
