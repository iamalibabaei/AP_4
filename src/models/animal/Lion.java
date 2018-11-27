package models.animal;

import models.Entity;

public class Lion extends WildAnimal
{
    public static final int SELL_MONEY = 0, OCCUPATION_SPACE = 0;

    public Lion()
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

}
