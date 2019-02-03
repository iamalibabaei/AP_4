package models.Multiplayer.Node;

import models.Multiplayer.Node.Runnables.getDataRunnable;

import java.io.*;
import java.net.Socket;

public class Client implements NetworkNode
{
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    private void initIOStreams(InputStream is, OutputStream os) throws IOException
    {
        inputStream = new ObjectInputStream(is);
        outputStream = new ObjectOutputStream(os);
    }

    @Override
    public void startReceiveDataThread() throws IOException
    {
        new Thread(new getDataRunnable<>(inputStream, this)).start();
    }

}
