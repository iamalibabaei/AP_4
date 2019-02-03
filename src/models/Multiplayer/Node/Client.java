package models.Multiplayer.Node;

import models.Multiplayer.Node.Runnables.GetDataRunnable;
import models.Multiplayer.Packet.Handleable;

import java.io.*;
import java.net.Socket;

public class Client implements NetworkNode
{
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

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
