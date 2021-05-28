package RaceGame;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

public class Server {
    public static void piConnect() {

        int port = 6666;

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            InetAddress inetAddress = InetAddress.getLocalHost();
            System.out.println("Server opened at: "+inetAddress.getHostAddress());

            while (true) {
                System.out.println(serverSocket);
                Socket socket = serverSocket.accept();

                System.out.println("New client connected");

                InputStream inputStream = socket.getInputStream();
                // create a DataInputStream so we can read data from it.
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                String message = dataInputStream.readUTF();
                System.out.println("The message sent from the socket was: " + message);
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
