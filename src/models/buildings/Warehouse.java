package models.buildings;

import models.Viewable;
import models.exceptions.AlreadyAtMaxLevelException;
import models.exceptions.InsufficientResourcesException;
import models.exceptions.NotEnoughSpaceException;
import models.interfaces.Upgradable;
import models.objects.Item;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

// todo add all feature for truck

public class Warehouse extends Viewable implements Upgradable
{
    public static final String NAME = "warehouse";
    public static final int[] CAPACITY = {50, 150, 300, 600}, UPGRADE_COST = {200, 250, 300};
    private static final int MAX_LEVEL = 3;
    private static Warehouse instance = new Warehouse();
    private int level;
    private int remainingCapacity;

    public EnumMap<Item.Type, Integer> getStoredItems() {
        return storedItems;
    }

    private EnumMap<Item.Type, Integer> storedItems;

    private Warehouse()
    {
        level = 0;
        storedItems = new EnumMap<>(Item.Type.class);
        remainingCapacity = CAPACITY[level];
    }

    public static Warehouse getInstance()
    {
        return instance;
    }

    public int getRemainingCapacity()
    {
        return remainingCapacity;
    }

    public void remove(EnumMap<Item.Type, Integer> items)
    {
        for (Item.Type type : items.keySet())
        {
            storedItems.put(type, storedItems.get(type) - 1);
            remainingCapacity += type.OCCUPIED_SPACE;
        }
    }

    public int moveToWorkshop(Map<Item.Type, Integer> base, int maxCoefficient) throws InsufficientResourcesException
    {
        int maxResourceAvailable = 999;
        for (Map.Entry<Item.Type, Integer> entry : base.entrySet())
        {
            int maxResource = storedItems.getOrDefault(entry.getKey(), 0) / entry.getValue();
            maxResourceAvailable = Math.min(maxResourceAvailable, maxResource);
        }
        if (maxResourceAvailable == 0)
        {
            throw new InsufficientResourcesException();
        }
        maxResourceAvailable = Math.min(maxResourceAvailable, maxCoefficient);
        for (Map.Entry<Item.Type, Integer> entry : base.entrySet())
        {
            Integer newValue = storedItems.get(entry.getKey()) - entry.getValue() * maxResourceAvailable;
            storedItems.replace(entry.getKey(), newValue);
        }
        return maxResourceAvailable;
    }

    public List<Item> store(List<Item> items) throws NotEnoughSpaceException
    {
        boolean noSpaceForOneItem = true;
        List<Item> stored = new ArrayList<>();
        for (Item item : items)
        {
            if (remainingCapacity >= item.type.OCCUPIED_SPACE)
            {
                noSpaceForOneItem = false;
                storedItems.put(item.type, storedItems.getOrDefault(item.type, 0) + 1);
                stored.add(item);
            }
        }
        if (noSpaceForOneItem)
        {
            throw new NotEnoughSpaceException();
        }
        return stored;
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

    @Override
    public int getLevel() {
        return level;
    }

}
