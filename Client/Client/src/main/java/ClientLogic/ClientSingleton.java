package ClientLogic;


import java.io.IOException;
import java.net.Socket;

public class ClientSingleton {
    //only one instance of client is needed for the whole program, we implement it using a singleton
    private final static ClientSingleton instance = new ClientSingleton();

    public static ClientSingleton getInstance()
    {
        return instance;
    }

    private Client client;

    {
        try {
            client = new Client(new Socket("localhost", 27017));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  Client getClient()
    {
        return client;
    }
}
