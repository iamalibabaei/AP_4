package models.buildings;

import models.exceptions.AlreadyAtMaxLevelException;
import models.exceptions.InsufficientResourcesException;
import models.exceptions.IsWorkingException;
import models.interfaces.Time;
import models.interfaces.Upgradable;

// todo implement well last upgrade (automatic planting)

public class Well implements Upgradable, Time
{
    public static final int[] UPGRADE_COST = {250, 500}, REFILL_COST = {19, 17, 15};
    public static final int[] CAPACITY = {5, 7, 10}, REFILL_TIME = {3, 4, 3};
    private static final Well instance = new Well();
    private static final int MAX_LEVEL = 2;
    private static int level = 0;
    private boolean isRefilling;
    private int remainingWater;
    private int remainingTimeToRefill;

    private Well()
    {
        isRefilling = false;
        remainingWater = CAPACITY[level];
    }

    public static Well getInstance()
    {
        return instance;
    }

    public static int getLevel()
    {
        return level;
    }

    public void extractWater() throws InsufficientResourcesException
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
        remainingTimeToRefill = REFILL_TIME[level];
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
            remainingTimeToRefill--;
            if (remainingTimeToRefill == 0)
                refill();
        }
    }

    private void refill()
    {
        isRefilling = false;
        remainingWater = CAPACITY[level];
    }

}
