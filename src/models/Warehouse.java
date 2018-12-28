package models;

import models.exceptions.AlreadyAtMaxLevelException;
import models.exceptions.ItemNotInWarehouseException;
import models.exceptions.NotEnoughSpaceException;
import models.interfaces.Upgradable;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Warehouse implements Upgradable
{
    public static final int[] CAPACITY = {50, 150, 300, 600}, UPGRADE_COST = {200, 250, 300};
    public static final int MAX_LEVEL = 3;
    private int level, remainingCapacity;
    private HashMap<Item.Type, Integer> items;

    public Warehouse()
    {
        level = 0;
        items = new HashMap<>();
        remainingCapacity = CAPACITY[level];
    }

    public void remove(Item.Type item, int count) throws ItemNotInWarehouseException
    {
        int freedSpace = Item.getOccupationSpace(item) * count;
        int currentCount = items.get(item);
        if (currentCount < count)
            throw new ItemNotInWarehouseException();
        remainingCapacity += freedSpace;
        items.put(item, currentCount - count);
    }

    public void store(HashMap<Item.Type, Integer> items) throws NotEnoughSpaceException
    {
        // todo knapp-sack algorithm
        AtomicBoolean enoughSpaceForOneItem = new AtomicBoolean(false);
        for (HashMap.Entry<Item.Type, Integer> entry : items.entrySet())
        {
            enoughSpaceForOneItem.set(true);
            int requiredCapacity = entry.getValue() * Item.getOccupationSpace(entry.getKey());
            if (remainingCapacity > requiredCapacity)
            {
                remainingCapacity -= requiredCapacity;
                Integer newValue = this.items.getOrDefault(entry.getKey(), 0) + entry.getValue();
                this.items.replace(entry.getKey(), newValue);
                this.items.putIfAbsent(entry.getKey(), newValue);
            }
        }
        if (!enoughSpaceForOneItem.get())
        {
            throw new NotEnoughSpaceException();
        }
    }

    private int computeSpace(HashMap<Item.Type, Integer> items)
    {
        AtomicInteger ret = new AtomicInteger();
        for (HashMap.Entry<Item.Type, Integer> entry : items.entrySet())
        {
            ret.addAndGet(Item.getOccupationSpace(entry.getKey()) * entry.getValue());
        }
        return ret.get();
    }

    @Override
    public void upgrade() throws AlreadyAtMaxLevelException
    {
        if (level == MAX_LEVEL)
        {
            throw new AlreadyAtMaxLevelException();
        }
        level++;
    }

    @Override
    public int getUpgradeCost() throws AlreadyAtMaxLevelException
    {
        if (level == MAX_LEVEL)
        {
            throw new AlreadyAtMaxLevelException();
        }
        return UPGRADE_COST[level];
    }

}
