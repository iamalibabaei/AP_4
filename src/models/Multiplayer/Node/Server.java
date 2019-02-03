package models.Multiplayer.Node;

import models.Multiplayer.Node.Runnables.getDataRunnable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements NetworkNode
{
    public static final int SERVER_PORT = 7999;
    private ServerSocket serverSocket;
    private ArrayList<Socket> clients;

    public Server() throws IOException
    {
        serverSocket = new ServerSocket(SERVER_PORT);
    }

    @Override
    public void startReceiveDataThread() throws IOException
    {
        for (Socket socket : clients)
        {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            new Thread(new getDataRunnable<>(inputStream, this)).start();
        }
    }

}
