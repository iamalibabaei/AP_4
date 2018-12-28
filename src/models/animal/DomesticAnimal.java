package models.animal;

import models.Entity;
import models.Item;
import models.interfaces.Buyable;

public class DomesticAnimal extends Animal implements Buyable
{
    int hungerRate;

    public DomesticAnimal()
    {
        super();
    }

    public Item produce()
    {

        return null;
    }

    @Override
    public void setTarget()
    {

    }

    @Override
    public int getSellMoney()
    {
        return 0;
    }

    @Override
    public void collide(Entity entity)
    {

    }

    @Override
    public int getOccupationSpace()
    {
        return 0;
    }

    @Override
    public int getBuyCost()
    {
        return 0;
    }

}
