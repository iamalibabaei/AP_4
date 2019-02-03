package models.transportation;

import models.exceptions.Messages;
import models.Viewable;
import models.interfaces.Time;
import models.interfaces.Upgradable;
import models.objects.Item;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;


public abstract class Transporter extends Viewable implements Upgradable, Time
{
    private final int[] UPGRADE_COST_LIST;
    private final int UPGRADE_SPEED_BOOST;
    private final int UPGRADE_CAPACITY_INCREASE;

    public boolean isWorking() {
        return isWorking;
    }

    protected boolean isWorking;

    public EnumMap<Item.Type, Integer> getList()
    {
        return list;
    }

    protected EnumMap<Item.Type, Integer> list;
    protected int remainingTimeToArrive;
    protected int capacity, timeToArrive, level;

    Transporter(int[] UPGRADE_COST_LIST, int UPGRADE_SPEED_BOOST, int UPGRADE_CAPACITY_INCREASE,
                int BASE_TIME_TO_ARRIVE, int capacity)
    {
        isWorking = false;
        this.capacity = capacity;
        this.UPGRADE_COST_LIST = UPGRADE_COST_LIST;
        this.UPGRADE_SPEED_BOOST = UPGRADE_SPEED_BOOST;
        this.UPGRADE_CAPACITY_INCREASE = UPGRADE_CAPACITY_INCREASE;
        timeToArrive = BASE_TIME_TO_ARRIVE;
        level = 0;
        list = new EnumMap<>(Item.Type.class);
    }

    public void addToList(Item.Type itemType, int count) throws IOException
    {
        if (capacity - usedCapacity() < itemType.OCCUPIED_SPACE * count)
        {
            throw new IOException(Messages.TRANSPORTER_STASH_FULL);
        }
        if (list.containsKey(itemType))
        {
            list.put(itemType, count + list.get(itemType));
        } else
        {
            list.put(itemType, count);
        }
    }

    public abstract int computePrice();

    public void removeFromList(Item.Type type, int number)
    {
        list.put(type, list.get(type) - number);
    }

    private int usedCapacity()
    {
        int usedCapacity = 0;
        for (Map.Entry<Item.Type, Integer> entry : list.entrySet())
        {
            int elementOccupation = entry.getKey().OCCUPIED_SPACE * entry.getValue();
            usedCapacity += elementOccupation;
        }
        return usedCapacity;
    }

    public void go()
    {
        isWorking = true;
        remainingTimeToArrive = timeToArrive;
    }

    @Override
    public void upgrade() throws IOException
    {
        if (level == 3)
        {
            throw new IOException(Messages.UPGRADE_BEYOND_MAX_LEVEL);
        }
        level++;
        timeToArrive -= UPGRADE_SPEED_BOOST;
        capacity = UPGRADE_CAPACITY_INCREASE + capacity;
    }

    @Override
    public int getUpgradeCost() throws IOException
    {
        if (level == 3)
        {
            throw new IOException(Messages.ALREADY_AT_MAX_LEVEL);
        }
        return UPGRADE_COST_LIST[level];
    }


    @Override
    public abstract void nextTurn();

    public void clearStash()
    {
        list.clear();
    }

}
