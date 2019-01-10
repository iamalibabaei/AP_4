package models.animal;

import models.Entity;
import models.Map;
import models.Point;
import models.interfaces.Time;

import java.util.Random;

public abstract class Animal extends Entity implements Time {
    Point target = new Point(-1, -1);
    public final Type type;
    protected Map map;
    private static int speed = 5;
    Point direction;

    public enum Type {
        CAT("cat"), DOG("dog"), LION("lion"), BEAR("bear"), SHEEP("sheep"), HEN("hen"), COW("cow");

        public String NAME;

        Type(String NAME) {
            this.NAME = NAME;
        }
    }

    Animal(double x, double y, Type type, Map map) {
        super(x, y);
        target = null;
        this.map = map;
        this.type = type;
        direction = new Point(0, 0);
    }


    public void setTarget() // default move is random
    {
        Random random = new Random();
        target.setX(random.nextDouble() * Map.WIDTH);
        target.setY(random.nextDouble() * Map.WIDTH);
    }

    public abstract void collide(Entity entity);

    //if (target == null) randomWalk
    public void move() {

        direction.setX(target.getX() - this.getCoordinates().getX());
        direction.setX(target.getY() - this.getCoordinates().getY());
        direction = direction.normalize(direction);

        this.getCoordinates().setX(this.getCoordinates().getX() + direction.getX() * Animal.speed);
        this.getCoordinates().setY(this.getCoordinates().getY() + direction.getY() * Animal.speed);

    }

    @Override
    public void nextTurn() {
        setTarget();
        move();
    }

}
