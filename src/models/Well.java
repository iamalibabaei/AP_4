package models;

import models.exceptions.AlreadyAtMaxLevelException;
import models.exceptions.InsufficientResourcesException;
import models.exceptions.IsWorkingException;
import models.interfaces.Time;
import models.interfaces.Upgradable;

public class Well implements Upgradable, Time
{
    public static final int[] CAPACITY = {5, 7, 10}, REFILL_COST = {19, 17, 15}, REFILL_TIME = {3, 4, 3};
    public static final int[] UPGRADE_COST = {250, 500};
    public static final int MAX_LEVEL = 2;
    private boolean isRefilling;
    private int level, remainingWater, timer;

    public void putGrass() throws InsufficientResourcesException
    {
        if (remainingWater == 0)
            throw new InsufficientResourcesException();
        remainingWater--;
    }

    public Well()
    {
        isRefilling = false;
        level = 0;
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
