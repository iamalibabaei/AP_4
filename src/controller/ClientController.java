package controller;

import models.Multiplayer.Node.Client;

import java.io.IOException;
import java.net.InetAddress;

public class ClientController
{
    private static ClientController instance = new ClientController();
    private Client client;

    public static ClientController getInstance()
    {
        return instance;
    }

    public void setup(String address) throws IOException
    {
        client = new Client(address);
    }

    private ClientController()
    {
    }


}
