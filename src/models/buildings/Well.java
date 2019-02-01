package models.buildings;

import models.Viewable;
import models.exceptions.AlreadyAtMaxLevelException;
import models.exceptions.InsufficientResourcesException;
import models.exceptions.IsWorkingException;
import models.interfaces.Time;
import models.interfaces.Upgradable;

// todo implement well last upgrade (automatic planting)

public class Well extends Viewable implements Upgradable, Time
{
    public static final int[] UPGRADE_COST = {250, 500}, REFILL_COST = {19, 17, 15};
    public static final int[] CAPACITY = {5, 7, 10}, REFILL_TIME = {4, 4, 3};
    private static final int MAX_LEVEL = 2;
    private int level;
    private boolean isRefilling;
    private int remainingWater;
    private int remainingTimeToRefill; // only needed when refilling

    private static Well instance = new Well();

    public static Well getInstance() {
        return instance;
    }

    private Well()
    {
        isRefilling = false;
        level = 0;
        remainingWater = CAPACITY[level];
    }

    @Override
    public int getLevel()
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
