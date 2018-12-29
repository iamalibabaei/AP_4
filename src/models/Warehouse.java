package models;

import models.exceptions.AlreadyAtMaxLevelException;
import models.exceptions.InsufficientResourcesException;
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

    public void moveToSeller(Item.Type item, int count) throws ItemNotInWarehouseException
    {
        int freedSpace = item.OCCUPIED_SPACE * count;
        int currentCount = items.get(item);
        if (currentCount < count)
            throw new ItemNotInWarehouseException();
        remainingCapacity += freedSpace;
        items.put(item, currentCount - count);
    }

    public AtomicInteger moveToWorkshop(HashMap<Item.Type, Integer> base, int maxCoefficient) throws InsufficientResourcesException
    {
        AtomicInteger maxResourceAvailable = new AtomicInteger(999);
        for (Item.Type type : base.keySet())
        {
            int maxResource = items.getOrDefault(type, 0) / base.get(type);
            maxResourceAvailable.set(Math.min(maxResourceAvailable.get(), maxResource));
        }
        if (maxResourceAvailable.get() == 0)
            throw new InsufficientResourcesException();
        maxResourceAvailable.set(Math.min(maxResourceAvailable.get(), maxCoefficient));
        for (Item.Type type : base.keySet())
        {
            Integer newValue = this.items.get(type) - base.get(type) * maxResourceAvailable.get();
            this.items.replace(type, newValue);
        }
        return maxResourceAvailable;
    }

    public void store(HashMap<Item.Type, Integer> items) throws NotEnoughSpaceException
    {
        // todo knapp-sack algorithm
        AtomicBoolean enoughSpaceForOneItem = new AtomicBoolean(false);
        for (HashMap.Entry<Item.Type, Integer> entry : items.entrySet())
        {
            enoughSpaceForOneItem.set(true);
            int requiredCapacity = entry.getValue() * entry.getKey().OCCUPIED_SPACE;
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
            ret.addAndGet(entry.getKey().OCCUPIED_SPACE * entry.getValue());
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
