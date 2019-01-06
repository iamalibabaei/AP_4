package models.animal;

import models.Entity;
import models.Point;
import models.interfaces.Time;
import models.map.Map;

import java.util.Random;

public abstract class Animal extends Entity implements Time
{
    Point target;
    protected Type type;
    protected Map map;

    public enum Type
    {
        CAT("cat"), DOG("dog"), LION("lion"), BEAR("bear"), SHEEP("sheep"), HEN("hen"), COW("cow");

        public String NAME;

        Type(String NAME)
        {
            this.NAME = NAME;
        }
    }

    Animal(int x, int y, Type type, Map map)
    {
        super(x, y);
        target = null;
        this.map = map;
        this.type = type;
    }

    public void setTarget() // default move is random
    {
        target = null;
    }
    public abstract void collide(Entity entity);

    //if (target == null) randomWalk
    public void move()
    {
        if (target == null){
            Random random = new Random();
            this.setX(this.getX()  + (random.nextInt(3 ) - 1));
            this.setY(this.getY()  + (random.nextInt(3 ) - 1));

            if (this.getX() < 0) {this.setX(this.getX() + random.nextInt(2) + 1); }
            if (this.getY() < 0) {this.setY(this.getY() + random.nextInt(2) + 1); }
            if (this.getX() > map.WIDTH) { this.setX(this.getX() - random.nextInt(2) + 1); }
            if (this.getY() > map.HEIGHT) {this.setY(this.getY() - random.nextInt(2) + 1); }
        }
        else {
            if (this.getX()  < target.getX()){
                this.setX(this.getX() + 1);
            }
            else if (this.getX()  > target.getX()){
                this.setX(this.getX() - 1);
            }
            if (this.getY() < target.getY()){
                this.setY(this.getY() + 1);
            }
            else if (this.getY() > target.getY()){
                this.setY(this.getY() - 1);
            }
        }
    }

    @Override
    public void nextTurn(){
            setTarget();
        move();
    }

}
