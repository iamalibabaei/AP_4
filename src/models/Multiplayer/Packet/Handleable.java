package models.Multiplayer.Packet;

import models.Multiplayer.Node.NetworkNode;

@FunctionalInterface
public interface Handleable<T extends NetworkNode>
{
    void handleIt(T handler);

}
