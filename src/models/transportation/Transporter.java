package models.transportation;

import models.Item;
import models.exceptions.IsWorkingException;
import models.exceptions.NotEnoughSpaceException;
import models.interfaces.Upgradable;

import java.util.HashMap;

public abstract class Transporter implements Upgradable
{
    int capacity, speed, level, arriveToFarm;//speed = arriving to destination per turn
    protected boolean isWorking = false;

    protected HashMap<Item.Type, Integer> list;

    public void addToList(Item.Type itemType, int number) throws IsWorkingException, NotEnoughSpaceException {
        if (isWorking) {
            throw new IsWorkingException();
        }
        if (this.capacity - this.usedCapacity() < itemType.occupiedSpace * number) {
            throw new NotEnoughSpaceException();
        }
        if (list.containsKey(itemType)) {
            list.put(itemType, number + list.get(itemType));
        } else {
            list.put(itemType, number);
        }
    }
    
    public int usedCapacity() {
        int usedCapacity = 0;
        for (Item.Type itemType : list.keySet())
        {
            int elementOccupation = itemType.occupiedSpace * list.get(itemType);
            usedCapacity += elementOccupation;
        }
        return usedCapacity;
    }
    
    public abstract void go() throws Exception;

    public abstract void turn();

}
