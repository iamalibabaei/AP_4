package models.transportation;

import models.Item;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Transporter
{
    int Capacity, Speed, level;
    private HashMap<Item, Integer> list;

    public abstract void go();

}
