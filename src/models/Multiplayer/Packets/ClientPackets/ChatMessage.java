package models.Multiplayer.Packets.ClientPackets;

import models.Multiplayer.Node.Client;
import models.Multiplayer.Packets.Handleable;
import view.multiplayer.Panel;

public class ChatMessage implements Handleable<Client>
{
    private String message;
    // todo sender
    boolean isPrivate;

    @Override
    public void handleIt(Client handler)
    {
        Panel.getInstance().getMessage(message);
    }

    @Override
    public String toString()
    {
        return null; // todo
    }

}
