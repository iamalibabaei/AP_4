package models;

import models.interfaces.Upgradable;

public class Well implements Upgradable
{
    public static final int BASE_CAPACITY = 0, CAPACITY_UPGRADE_INCREASE = 0, BASE_REFILL_TIME = 0;
    boolean isRefilling;
    private int capacity;
    private int usedCapacity;
    private int refillTime;
    private int refillFinishTime;

    public Well(int capacity)
    {
        this.capacity = capacity;
        isRefilling = false;
    }

    public void issueRefill(int time)
    {

    }

    private void refill(int time)
    {

    }

    @Override
    public void issueUpgrade()
    {

    }

    @Override
    public void upgrade()
    {

    }

}
