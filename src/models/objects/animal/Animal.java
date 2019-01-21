package models.objects.animal;

import models.Map;
import models.exceptions.NotEnoughSpaceException;
import models.interfaces.Time;
import models.objects.Entity;
import models.objects.Item;
import models.objects.Point;

import java.util.Random;

public abstract class Animal extends Entity implements Time
{
    private static int speed = 5;
    public final Type type;
    protected Map map;
    Point target;
    Point direction;

    Animal(Point point, Type type)
    {
        super(point);
        target = null;
        this.map = Map.getInstance();
        this.type = type;
        direction = new Point(0, 0);
    }

    public abstract void collide(Entity entity) throws NotEnoughSpaceException;

    @Override
    public void nextTurn()
    {
        setTarget();
        move();
    }

    public void setTarget() // default move is random
    {
        Random random = new Random();
        target.setX(random.nextDouble() * Map.WIDTH);
        target.setY(random.nextDouble() * Map.WIDTH);
    }

    //if (target == null) randomWalk
    public void move()
    {

        direction.setX(target.getX() - this.getCoordinates().getX());
        direction.setX(target.getY() - this.getCoordinates().getY());
        direction = direction.normalize(direction);

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
