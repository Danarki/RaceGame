package RaceGame;

import java.io.*;
import java.net.*;

public class PiController extends MainProgram{
    public static void connect() {
        try{
            Socket soc=new Socket("localhost",12345);
            DataOutputStream dataOut =new DataOutputStream(soc.getOutputStream());
            DataInputStream dataIn = new DataInputStream(soc.getInputStream());
            String msg=(String)dataIn.readUTF();
            System.out.println("Server: "+msg);
            dataOut.writeUTF("Ok Boss");
            dataOut.flush();
            dataOut.close();
            soc.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        connect();
    }
}