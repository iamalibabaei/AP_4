package models.Multiplayer.Packet;

import models.Multiplayer.Node.NetworkNode;

import java.io.Serializable;

@FunctionalInterface
public interface Handleable<T extends NetworkNode> extends Serializable
{
    void handleIt(T handler);

}
