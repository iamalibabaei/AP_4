package models.transportation;

import models.Item;
import models.interfaces.Upgradable;

import java.util.HashMap;

public abstract class Transporter implements Upgradable
{
    int capacity, speed, level, arriveToFarm;//speed = arriving to destination per turn
    protected boolean isWorking = false;
    protected HashMap<Item.Type, Integer> list;

    public boolean addToList(Item.Type itemType, int number) {
        if (isWorking) {
            return false;
        }
        if (this.capacity - this.usedCapacity() <
                number * ((Storable) Item.Type.TYPE_INDEXED(itemType.getType())).getOccupationSpace()) {
            return false;
        }
        if (list.containsKey(itemType)) {
            list.put(itemType, number + list.get(itemType));
        } else {
            list.put(itemType, number);
        }
        return true;
    }
    
    public int usedCapacity() {
        int usedCapacity = 0;
        for (Item.Type itemType : list.keySet())
        {
            int elementOccupation =((Storable) Item.Type.TYPE_INDEXED(itemType.getType())).getOccupationSpace();
            elementOccupation = elementOccupation * list.get(itemType);
            usedCapacity += elementOccupation;
        }
        return usedCapacity;
    }
    
    public abstract void go() throws Exception;

    public abstract void turn();

}
