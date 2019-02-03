package models.Multiplayer.Node;

import java.io.IOException;

@FunctionalInterface
public interface NetworkNode
{
    void startReceiveDataThread() throws IOException;

}
