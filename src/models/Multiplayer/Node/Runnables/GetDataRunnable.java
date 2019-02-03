package models.Multiplayer.Node.Runnables;

import models.Multiplayer.Node.NetworkNode;
import models.Multiplayer.Packet.Handleable;

import java.io.IOException;
import java.io.ObjectInputStream;

public class GetDataRunnable<T extends NetworkNode> implements Runnable
{
    private ObjectInputStream inputStream;
    private T listener;

    public GetDataRunnable(ObjectInputStream inputStream, T listener)
    {
        this.inputStream = inputStream;
        this.listener = listener;
    }


    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                Handleable<T> packet = (Handleable<T>) inputStream.readObject();
                packet.handleIt(listener);
            } catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }
    }

}
