package models.objects.animals;

import javafx.scene.image.ImageView;
import models.Map;
import models.Viewable;
import models.interfaces.Time;
import models.objects.Entity;
import models.objects.Item;
import models.objects.Point;
import view.gameScene.View;


import java.util.HashMap;

public abstract class Animal extends Entity implements Time
{
    private static final int SPEED = 2;
    public final Animal.Type type;
    protected Map map;
    protected Point target;
    private boolean isArrived = false;

    public Point getTarget() {
        return target;
    }

    protected HashMap<stateKind, ImageView> converter;

    Animal(Point point, Animal.Type type)
    {
        super(point);
        state = stateKind.LEFT;
        target = null;
        map = Map.getInstance();
        this.type = type;
        converter = new HashMap<>();
        buildHashmap();
    }

    protected abstract void buildHashmap();


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


    @Override
    public void updateImageView() {
        imageView = converter.get(state);
    }

    public void setTarget() // default move is random
    {
        if (isArrived) {
            target = Point.randomPoint(Map.WIDTH, Map.HEIGHT);
        }
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

        double teta = Math.atan(direction.getY() / direction.getX());
        if ( - 0.4 < teta && teta <0.4) {
            state = stateKind.RIGHT;
        } else if ( 0.4 < teta && teta <0.9) {
            state = stateKind.UP_RIGHT;
        } else if ( 0.9 > teta && teta > -1.1) {
            state = stateKind.UP;
        } else if (- 1.1 > teta && teta > -4.6) {
            state = stateKind.UP_LEFT;
        } else if (- 0.9 < teta && teta <0.9)


        if (direction.getY() == 0 && direction.getX() == 0) {
            state = stateKind.EAT;
        }
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

    public boolean isArrived() {
        return isArrived;
    }

    public void setArrived(boolean arrived) {
        isArrived = arrived;
    }
}
