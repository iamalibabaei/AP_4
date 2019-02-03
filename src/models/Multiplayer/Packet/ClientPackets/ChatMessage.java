package models.Multiplayer.Packet.ClientPackets;

import models.Multiplayer.Node.Client;
import models.Multiplayer.Packet.Handleable;

public class ChatMessage implements Handleable<Client>
{
    private String message;

    @Override
    public void handleIt(Client handler)
    {
    }

}
