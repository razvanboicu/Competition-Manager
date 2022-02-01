import ServerLogic.GameLogic;
import ServerLogic.Handler;
import database.dao.TeamDao;
import database.dao.UserDao;
import database.model.TeamEntity;
import database.model.UserEntity;
import database.model.enums.Role;
import org.eclipse.persistence.internal.sessions.DirectCollectionChangeRecord;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket ServerListenerSocket;

    public Server(ServerSocket svSocket)
    {
        this.ServerListenerSocket=svSocket;
    }

    private void Run()
    {
        try {
            while (!ServerListenerSocket.isClosed()) {
                Socket ClientSocket = ServerListenerSocket.accept(); //blocking function, will stay here until a client connects
                Handler handler= new Handler(ClientSocket);
                Thread thread= new Thread(handler);
                thread.start();
            }
        }
        catch(IOException exception)
        {
            CloseServerSocket();
        }
    }

    public void CloseServerSocket()
    {
        if(ServerListenerSocket!=null) {
            try {
                ServerListenerSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting server");
        GameLogic.GenerateCurrentStage();
        GameLogic.GenerateClasament();
        try {
            ServerSocket socket = new ServerSocket(27017);
            Server server = new Server(socket);
            server.Run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
