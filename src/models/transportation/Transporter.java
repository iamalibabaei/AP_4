package models.transportation;

import models.Item;
import models.interfaces.Upgradable;

import java.util.HashMap;

public abstract class Transporter implements Upgradable
{
    int capacity, speed, level;
    private HashMap<Item, Integer> list;

    public abstract void go();

}
