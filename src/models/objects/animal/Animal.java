package models.objects.animal;

import models.Map;
import models.exceptions.NotEnoughSpaceException;
import models.interfaces.Time;
import models.objects.Entity;
import models.objects.Item;
import models.objects.Point;

public abstract class Animal extends Entity implements Time
{
    private static final int speed = 5;
    public final Type type;
    Map map;
    Point target;

    Animal(Point point, Type type)
    {
        super(point);
        target = null;
        this.map = Map.getInstance();
        this.type = type;
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
        Point direction = new Point(target.getX() - this.getCoordinates().getX(),
                target.getY() - this.getCoordinates().getY());
        direction.normalize();
        this.getCoordinates().setX(this.getCoordinates().getX() + direction.getX() * Animal.speed);
        this.getCoordinates().setY(this.getCoordinates().getY() + direction.getY() * Animal.speed);
    }

    public enum Type
    {
        CAT("cat", 2500, Item.Type.NONE), DOG("dog", 2600, Item.Type.NONE),
        LION("lion", -1, Item.Type.NONE), BEAR("bear", -1, Item.Type.NONE),
        SHEEP("sheep", 1000, Item.Type.FABRIC), HEN("hen", 100, Item.Type.EGG),
        COW("cow", 10000, Item.Type.MILK);

        public final int BUY_COST;
        public final Item.Type PRODUCT;
        public final String NAME;

        Type(String NAME, int BUY_COST, Item.Type PRODUCT)
        {
            this.NAME = NAME;
            this.BUY_COST = BUY_COST;
            this.PRODUCT = PRODUCT;
        }

    }

}
