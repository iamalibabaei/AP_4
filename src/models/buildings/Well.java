package models.buildings;

import models.exceptions.AlreadyAtMaxLevelException;
import models.exceptions.InsufficientResourcesException;
import models.exceptions.IsWorkingException;
import models.interfaces.Time;
import models.interfaces.Upgradable;

public class Well implements Upgradable, Time
{
    public static final int[] UPGRADE_COST = {250, 500}, REFILL_COST = {19, 17, 15};
    private static final Well ourInstance = new Well();
    private static final int[] CAPACITY = {5, 7, 10}, REFILL_TIME = {3, 4, 3};
    private static final int MAX_LEVEL = 2;
    private static int level;
    private boolean isRefilling;
    private int remainingWater, timer;

    public Well()
    {
        isRefilling = false;
        level = 0;
    }

    public static Well getInstance()
    {
        return ourInstance;
    }

    public static int getLevel()
    {
        return level;
    }

    public boolean isRefilling()
    {
        return isRefilling;
    }

    public int getRemainingWater()
    {
        return remainingWater;
    }

    public void putGrass() throws InsufficientResourcesException
    {
        if (remainingWater == 0)
            throw new InsufficientResourcesException();
        remainingWater--;
    }

    public void issueRefill() throws IsWorkingException
    {
        if (isRefilling)
            throw new IsWorkingException();
        isRefilling = true;
        timer = REFILL_TIME[level];
    }

    @Override
    public void upgrade() throws AlreadyAtMaxLevelException
    {
        if (level == MAX_LEVEL)
            throw new AlreadyAtMaxLevelException();
        level++;
    }

    @Override
    public int getUpgradeCost() throws AlreadyAtMaxLevelException
    {
        if (level == MAX_LEVEL)
            throw new AlreadyAtMaxLevelException();
        return UPGRADE_COST[level];
    }

    @Override
    public void nextTurn()
    {
        if (isRefilling)
        {
            timer--;
            if (timer == 0)
                refill();
        }
    }

    private void refill()
    {
        isRefilling = false;
        remainingWater = CAPACITY[level];
    }

}
