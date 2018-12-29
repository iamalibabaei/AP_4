package models.animal;

import models.Entity;
import models.map.Map;
public abstract class Animal extends Entity
{
    protected Entity target;

    protected Map map;

    public Animal(int x, int y, Map map)
    {
        super(x, y);
        target = null;
        this.map = map;
    }

    public abstract void setTarget();

    //if (target == null) randomWalk
    public void move()
    {

    }

    public abstract void collide(Entity entity);

    public enum Type
    {
        DOG, CAT, SHEEP, HEN, COW, BEAR, LION;
    }

}
