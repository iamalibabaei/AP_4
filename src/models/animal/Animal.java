package models.animal;

import models.Entity;

public abstract class Animal extends Entity
{
    protected Entity target;

    public Animal()
    {
        target = null;
    }

    public abstract void setTarget();

    public abstract int getSellMoney();

    public void move()
    {

    }

    public abstract void collide(Entity entity);

    public enum Type
    {
        DOG, CAT, SHEEP, HEN, COW, BEAR, LION;
    }

}
