package ServerLogic;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Handler implements Runnable {

    private static ArrayList<Handler> HandlerArray=new ArrayList<>();
    private BufferedReader ReadBuffer;
    private BufferedWriter WriteBuffer;
    private Socket ClientSocket;

    public Handler(Socket socket)
    {
        try {
            this.ClientSocket = socket;
            this.WriteBuffer = new BufferedWriter(new OutputStreamWriter(ClientSocket.getOutputStream()));
            this.ReadBuffer = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));
        }
        catch (IOException exception) {
            exception.printStackTrace();
            ShutdownSocket();
        }
    }

    @Override
    public void run() {
       String ReceivedMessage,SentMessage;
       while(ClientSocket.isConnected())
       {
           ReceivedMessage = ReceiveMsg();
           System.out.println("Reading message:"+ReceivedMessage+'\n');
           String toSend=GetRequestType(ReceivedMessage);
           SendMsg(toSend);
       }
    }

    private String ReceiveMsg()
    {
        try {
            return ReadBuffer.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void SendMsg(String toSend)
    {
        try {
            System.out.println("Sending message:"+toSend);
            WriteBuffer.write(toSend);
            WriteBuffer.newLine();
            WriteBuffer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String GetRequestType(String request)
    {
      String[] tokens= request.split(";",0);
      if(tokens[0].equals("NOTIFICATIONTHREAD")) {
          HandlerArray.add(this);
      }
      if(tokens[0].equals("NOTIFICATION"))
          return HandleNotification(tokens[1]);
      if(tokens[0].equals("LOGIN"))
          return UserLogic.HandleLoginRequest(tokens[1], tokens[2]);
      if(tokens[0].equals("USERLIST"))
          return UserLogic.GetListOfUsers();
      if(tokens[0].equals("STAGELIST"))
          return StageLogic.GetListOfStages();
      if(tokens[0].equals("TEAMLIST"))
          return TeamLogic.GetListOfTeams();
      if(tokens[0].equals("NEWUSER"))
          return UserLogic.UpdateNewUser(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6]);
      if(tokens[0].equals("NEWSTAGE"))
          return StageLogic.UpdateNewStage(tokens[1]);
      if(tokens[0].equals("GAMECHECK"))
          return GameLogic.CheckPossibleGame();
      if(tokens[0].equals("DELETEUSER"))
          return UserLogic.DeleteUser(tokens[1]);
      if(tokens[0].equals("UPDATEUSER"))
          return UserLogic.UpdateUser(tokens[1], tokens[2], tokens[3]);
      if(tokens[0].equals("NEWTEAM"))
          return TeamLogic.UpdateNewTeam(tokens[1]);
      if(tokens[0].equals("DELETESTAGE"))
          return StageLogic.DeleteStage(tokens[1]);
      if(tokens[0].equals("DELETETEAM"))
          return TeamLogic.DeleteTeam(tokens[1]);
      if(tokens[0].equals("UPDATESTAGE"))
          return StageLogic.UpdateStage(tokens[1],tokens[2]);
      if(tokens[0].equals("UPDATETEAM"))
          return TeamLogic.UpdateTeam(tokens[1],tokens[2]);
      if(tokens[0].equals("USERCLASAMENT"))
          return GameLogic.SendUserClasament(Integer.parseInt(tokens[1]));
      if(tokens[0].equals("TEAMCLASAMENT"))
          return GameLogic.SendTeamClasament(Integer.parseInt(tokens[1]));
      if(tokens[0].equals("NEWENTRY"))
          return GameLogic.UpdateClasaments(tokens[1],tokens[2]);
      if(tokens[0].equals("ENTRYSTATUS"))
          return GameLogic.GetEntryStatusForUser(tokens[1],Integer.parseInt(tokens[2]));
      if(tokens[0].equals("CURRENTSTAGE"))
          return StageLogic.GetCurrentStage();
      if(tokens[0].equals("STAGEINFO"))
          return StageLogic.GetCurrentStageInfo(Integer.parseInt(tokens[1]),tokens[2]);
      if(tokens[0].equals("NEXTSTAGE"))
          return StageLogic.GetNextStage(tokens[1]);
      if(tokens[0].equals("PREVIOUSSTAGE"))
          return StageLogic.GetPreviousStage(tokens[1]);
      if(tokens[0].equals("GAMESTATUS"))
          return GameLogic.GetGameStatus(Integer.parseInt(tokens[1]));
      if(tokens[0].equals("FINALUSERCLASAMENT"))
          return GameLogic.GetFinalUserClasament();
      if(tokens[0].equals("FINALTEAMCLASAMENT"))
          return GameLogic.GetFinalTeamClasament();
      if(tokens[0].equals("GAMECOMPLETE"))
          return GameLogic.GetGameCompletionStatus();
      return "NOT YET IMPLEMENTED";
    }

    private String HandleNotification(String username)
    {
        for(Handler threadSocket:HandlerArray)
        {
            threadSocket.SendMsg(username);
            String response= threadSocket.ReceiveMsg();
            System.out.println(response);
                if(response.equals("FOUND"))
                    return "FOUND";
        }
        return "ERROR";
    }

    private void ShutdownSocket()
    {
            try {
                if(WriteBuffer!= null)
                    WriteBuffer.close();
                if(ReadBuffer!=null)
                    ReadBuffer.close();
                if(ClientSocket!=null)
                    ClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
        }
    }
}
