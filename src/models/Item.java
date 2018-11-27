package models;

import models.interfaces.Storable;

public class Item extends Entity implements Storable
{
    Type type;

    public Item(Type type)
    {
        this.type = type;
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
