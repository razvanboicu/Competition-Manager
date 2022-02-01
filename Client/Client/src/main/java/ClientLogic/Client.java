package ClientLogic;

import java.io.*;
import java.net.Socket;

public class Client {
    private BufferedReader ReadBuffer;
    private BufferedWriter WriteBuffer;
    private Socket Socket;

    public Client(Socket socket) {
        try {
            this.Socket = socket;
            this.WriteBuffer = new BufferedWriter(new OutputStreamWriter(Socket.getOutputStream()));
            this.ReadBuffer = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

        public void SendMessage(String toSend)
        {
            System.out.println("Sending message:"+toSend+'\n');
            try {
                WriteBuffer.write(toSend);
                WriteBuffer.newLine();
                WriteBuffer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String ReceiveMessage()
        {
            String toReceive = new String();
            try {
                toReceive= ReadBuffer.readLine();
                System.out.println("Received message: " + toReceive +'\n');
            } catch (IOException e) {
                e.printStackTrace();
            }
            return toReceive;
        }
}
