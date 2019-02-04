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
    private static final int SPEED = 5;
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

        double teta = Math.toDegrees(Math.atan(direction.getY() / direction.getX()));
        System.out.println("teta: " + teta);
        if (-22.5 <= teta  && teta< 22.5) {
            if (direction.getX() > 0) {
                state = stateKind.RIGHT;
            } else {
                state = stateKind.LEFT;
            }
        } else if (22.5 <= teta  && teta < 67.5) {
            if (direction.getX() > 0) {
                state = stateKind.UP_RIGHT;
            }else {
                state = stateKind.DOWN_LEFT;
            }
        } else if (67.5 <= teta  || teta <= -67.5) {
            if (direction.getX() > 0) {
                state = stateKind.UP;
            }else {
                state = stateKind.DOWN;
            }
        } else if (-67.5 < teta && teta < -22.5) {
            if (direction.getX() > 0) {
                state = stateKind.DOWN_RIGHT;
            } else {
                state = stateKind.UP_LEFT;
            }
        }


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
