package models.Multiplayer.Node;

import models.Multiplayer.Node.Runnables.GetDataRunnable;
import models.Multiplayer.Packets.Handleable;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public final class Client implements NetworkNode
{
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public Client(String address) throws IOException
    {
        socket = new Socket(address, Server.SERVER_PORT);
        initIOStreams(socket.getInputStream(), socket.getOutputStream());
        startReceiveDataThread();
    }

    public <T extends Handleable<Server>> void sendPacket(T packet) throws IOException
    {
        outputStream.writeObject(packet);
    }

    private void initIOStreams(InputStream is, OutputStream os) throws IOException
    {
        inputStream = new ObjectInputStream(is);
        outputStream = new ObjectOutputStream(os);
    }

    @Override
    public void startReceiveDataThread() throws IOException
    {
        new Thread(new GetDataRunnable<>(inputStream, this)).start();
    }

}
