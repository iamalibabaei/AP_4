package models;

import models.interfaces.Storable;

public class Item extends Entity implements Storable
{
    private final int SELL_MONEY, OCCUPATION_SPACE;
    Type type;

    public Item(Type type)
    {
        this.type = type;
        this.SELL_MONEY = 0;
        this.OCCUPATION_SPACE = 0;
    }

    @Override
    public int getOccupationSpace()
    {
        return 0;
    }

    enum Type
    {
        EGG, MILK, WOOL, EGG_POWDER, COOKIE, CAKE,
    }

}
