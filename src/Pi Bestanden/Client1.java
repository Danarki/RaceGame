import java.io.*;
import java.net.*;

public class Client1 {
    public static void main(String args[]) {
        try {
            String message = "Hier komt de input van de joystick";

            Socket socket = new Socket("192.168.178.25", 6666);
            System.out.println("Connected!");

            OutputStream outputStream = socket.getOutputStream();

            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            System.out.println("Sending string to the ServerSocket");

            dataOutputStream.writeUTF(message);

            System.out.println(socket.getInputStream());
            dataOutputStream.flush();
            dataOutputStream.close();

            System.out.println("Closing socket and terminating program.");
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}