package models.Multiplayer.Packets.ServerPackets;

import models.Multiplayer.Node.Server;
import models.Multiplayer.Packets.Handleable;

public class ChatMessage implements Handleable<Server>
{
    private String message;

    @Override
    public void handleIt(Server handler)
    {

    }

}
