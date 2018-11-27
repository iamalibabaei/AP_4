package models.animal;

import models.Entity;
import models.interfaces.Buyable;

public class Dog extends Animal implements Buyable
{
    public static final int BUY_COST = 0, SELL_MONEY = 0, OCCUPATION_SPACE = 0;

    public Dog()
    {
        super();
    }

    @Override
    public void setTarget()
    {

    }

    @Override
    public int getSellMoney()
    {
        return SELL_MONEY;
    }

    @Override
    public void collide(Entity entity)
    {

    }

    @Override
    public int getOccupationSpace()
    {
        return OCCUPATION_SPACE;
    }

    @Override
    public int getBuyCost()
    {
        return BUY_COST;
    }

}
