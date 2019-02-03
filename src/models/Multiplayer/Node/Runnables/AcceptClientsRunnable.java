package models.Multiplayer.Node.Runnables;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class AcceptClientsRunnable implements Runnable
{
    private ServerSocket serverSocket;
    private ArrayList<Socket> clients;

    public AcceptClientsRunnable(ServerSocket serverSocket, ArrayList<Socket> clients)
    {
        this.serverSocket = serverSocket;
        this.clients = clients;
    }

    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                clients.add(serverSocket.accept());
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

}
