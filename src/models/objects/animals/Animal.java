package models.objects.animals;

import models.Map;
import models.interfaces.Time;
import models.objects.Entity;
import models.objects.Item;
import models.objects.Point;

public abstract class Animal extends Entity implements Time
{
    private static final int SPEED = 5;
    public final Animal.Type type;
    protected Map map;
    protected Point target;

    Animal(Point point, Animal.Type type)
    {
        super(point);
        target = null;
        map = Map.getInstance();
        this.type = type;
    }

    @Override
    public String getName()
    {
        return type.toString().toLowerCase();
    }

    public abstract void collide(Entity entity);

    @Override
    public void nextTurn()
    {
        setTarget();
        move();
    }

    public void setTarget() // default move is random
    {
        target = Point.randomPoint(Map.WIDTH, Map.HEIGHT);
    }

    //if (target == null) randomWalk
    public void move()
    {
        if (target == null) {
            target = Point.randomPoint(Map.WIDTH, Map.HEIGHT);
        }
        Point direction = new Point(target.getX() - getCoordinates().getX(),
                target.getY() - getCoordinates().getY());
        direction.normalize();
        getCoordinates().setX(getCoordinates().getX() + direction.getX() * Animal.SPEED);
        getCoordinates().setY(getCoordinates().getY() + direction.getY() * Animal.SPEED);
    }

    public enum Type
    {
        HEN(100, Item.Type.EGG), SHEEP(1000, Item.Type.FABRIC), COW(10000, Item.Type.MILK),
        CAT(2500, Item.Type.NONE), DOG(2600, Item.Type.NONE),
        LION(-1, Item.Type.NONE), BEAR(-1, Item.Type.NONE);

        public final int BUY_COST;
        public final Item.Type PRODUCT;

        Type(int BUY_COST, Item.Type PRODUCT)
        {
            this.BUY_COST = BUY_COST;
            this.PRODUCT = PRODUCT;
        }

        public String getName()
        {
            return toString().toLowerCase();
        }

    }

}
