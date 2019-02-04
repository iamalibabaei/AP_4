package models.Multiplayer.Node;

import models.Multiplayer.Node.Runnables.AcceptClientsRunnable;
import models.Multiplayer.Node.Runnables.GetDataRunnable;
import models.Multiplayer.Packets.Handleable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public final class Server implements NetworkNode
{
    public static final int SERVER_PORT = 7999;
    private ServerSocket serverSocket;
    private ArrayList<Socket> clients;

    public Server() throws IOException
    {
        serverSocket = new ServerSocket(SERVER_PORT);
        clients = new ArrayList<>();
        startAcceptClientsThread();
        startReceiveDataThread();
    }

    public <T extends Handleable<Client>> void sendPacket(Socket client, T packet) throws IOException
    {
        ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
        outputStream.writeObject(packet);
    }

    private void startAcceptClientsThread()
    {
        new Thread(new AcceptClientsRunnable(serverSocket, clients)).start();
    }

    @Override
    public void startReceiveDataThread() throws IOException
    {
        for (Socket socket : clients)
        {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            new Thread(new GetDataRunnable<>(inputStream, this)).start();
        }
    }

}
