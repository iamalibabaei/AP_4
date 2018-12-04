package models.animal;

import models.Entity;
import models.map.Map;
public abstract class Animal extends Entity
{
    protected Entity target;

    protected Map map;

    public Animal(Map map)
    {
        target = null;
        this.map = map;
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
