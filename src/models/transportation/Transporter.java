package models.transportation;

import models.exceptions.AlreadyAtMaxLevelException;
import models.exceptions.IsWorkingException;
import models.exceptions.NotEnoughSpaceException;
import models.interfaces.Time;
import models.interfaces.Upgradable;
import models.objects.Item;

import java.util.HashMap;


public abstract class Transporter implements Upgradable, Time
{
    private final int[] UPGRADE_COST_LIST;
    private final int UPGRADE_SPEED_BOOST, UPGRADE_CAPACITY_INCREASE;
    protected boolean isWorking;
    HashMap<Item.Type, Integer> list;
    private int capacity, timeToArrive, level;
    int remainingTimeToArrive;

    Transporter(int[] UPGRADE_COST_LIST, int UPGRADE_SPEED_BOOST, int UPGRADE_CAPACITY_INCREASE,
                int BASE_TIME_TO_ARRIVE, int capacity)
    {
        isWorking = false;
        this.capacity = capacity;
        this.UPGRADE_COST_LIST = UPGRADE_COST_LIST;
        this.UPGRADE_SPEED_BOOST = UPGRADE_SPEED_BOOST;
        this.UPGRADE_CAPACITY_INCREASE = UPGRADE_CAPACITY_INCREASE;
        timeToArrive = BASE_TIME_TO_ARRIVE;
        this.level = 0;
    }

    public boolean isWorking()
    {
        return isWorking;
    }

    public int getLevel()
    {
        return level;
    }

    public int getTimeToArrive()
    {
        return timeToArrive;
    }

    public int getCapacity()
    {
        return capacity;
    }

    public int getRemainingTimeToArrive()
    {
        return remainingTimeToArrive;
    }

    public HashMap<Item.Type, Integer> getList()
    {
        return list;
    }

    public void addToList(Item.Type itemType, int number) throws IsWorkingException, NotEnoughSpaceException
    {
        // todo double-click feature
        if (isWorking)
        {
            throw new IsWorkingException();
        }
        if (this.capacity - this.usedCapacity() < itemType.OCCUPIED_SPACE * number)
        {
            throw new NotEnoughSpaceException();
        }
        if (list.containsKey(itemType))
        {
            list.put(itemType, number + list.get(itemType));
        } else
        {
            list.put(itemType, number);
        }
    }

    private int usedCapacity()
    {
        int usedCapacity = 0;
        for (Item.Type itemType : list.keySet())
        {
            int elementOccupation = itemType.OCCUPIED_SPACE * list.get(itemType);
            usedCapacity += elementOccupation;
        }
        return usedCapacity;
    }

    public void go() throws IsWorkingException
    {
        if (isWorking)
        {
            throw new IsWorkingException();
        }
        isWorking = true;
        remainingTimeToArrive = timeToArrive;
    }

    @Override
    public void upgrade() throws AlreadyAtMaxLevelException
    {
        if (level == 3)
        {
            throw new AlreadyAtMaxLevelException();
        }
        this.level++;
        this.timeToArrive = timeToArrive - UPGRADE_SPEED_BOOST;
        this.capacity = UPGRADE_CAPACITY_INCREASE + capacity;
    }

    @Override
    public int getUpgradeCost() throws AlreadyAtMaxLevelException
    {
        if (level == 3)
        {
            throw new AlreadyAtMaxLevelException();
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
