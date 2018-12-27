package models;

import java.util.HashMap;

public class Warehouse
{
    private int level, capacity, usedCapacity;
    private HashMap<Item.Type, Integer> items;

    public boolean remove(Item.Type item, int num)
    {
        return false;
    }

    public boolean isAvailable(Item.Type item, int num)
    {
        return false;
    }
    public boolean isAvailable(Item.Type item) { return false; }

    public boolean store(Entity entity)
    {
        return false;
    }

    private int remainingCapacity()
    {
        return 0;
    }

    //this method is for workshop, it gives the maximum number of type workshop wants
    //for example if there is 3 egg in warehouse getItemToWorkshop(EGG,5) returns 3  and decreases 3 egg from warehouse
    public int getItemToWorkshop(Item.Type item, int num) {
        return 0;
    }



}
