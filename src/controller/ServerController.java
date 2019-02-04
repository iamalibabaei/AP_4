package controller;

import models.Multiplayer.Node.Server;

import java.io.IOException;

public class ServerController
{
    private static ServerController instance = new ServerController();
    private Server server;

    private ServerController()
    {
    }

    public static ServerController getInstance()
    {
        return instance;
    }

    public void setup() throws IOException
    {
        server = new Server();
    }
}
