package models;

import java.util.HashMap;

public class Warehouse
{
    private int level, capacity, usedCapacity;
    private HashMap<Entity, Integer> items;

    public boolean remove(HashMap<Entity, Integer> items)
    {
        return false;
    }

    private boolean isAvailable(HashMap<Entity, Integer> items)
    {
        return false;
    }

    public boolean store(Entity entity)
    {
        return false;
    }

    private int remainingCapacity()
    {
        return 0;
    }

}
