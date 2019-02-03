package models.Multiplayer.Packet.ServerPackets;

import models.Multiplayer.Node.Server;
import models.Multiplayer.Packet.Handleable;

public class ChatMessage implements Handleable<Server>
{
    private String message;

    @Override
    public void handleIt(Server handler)
    {

    }

}
