package models;

import models.map.Map;
import models.transportation.Buyer;
import models.workshop.Workshop;

import java.util.ArrayList;

public class Game
{
    public static final int MAX_WORKSHOPS = 6, MAP_SIZE = 30;
    private static Game ourInstance = new Game();
    private int money;
    private Map map;
    private Warehouse warehouse;
    private Well well;
    private Buyer truck;
    private ArrayList<Workshop> workshops;
    private Mission mission;
    private int wildAnimalsInMap;

    public static Game getInstance()
    {
        return ourInstance;
    }

    public Map getMap() {
        return map;
    }

    public int getMoney() {
        return money;
    }

    private Game()
    {
        map = new Map();
        warehouse = new Warehouse();
        well = new Well();
        workshops = new ArrayList<>();
    }


}
