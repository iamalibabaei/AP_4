package models.animal;

import models.Entity;
import models.interfaces.Time;
import models.map.Map;

import java.util.Random;

public abstract class Animal extends Entity implements Time
{
    Entity target;
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
            x += (random.nextInt(3 ) - 1);
            y += (random.nextInt(3 ) - 1);

            if (x < 0) { x += random.nextInt(2) + 1; }
            if (y < 0) { y += random.nextInt(2) + 1; }
            if (x > map.WIDTH) { x -= random.nextInt(2) + 1;}
            if (y > map.HEIGHT) { y -= random.nextInt(2) + 1;}
        }
        else {
            if (x < target.getX()){
                x++;
            }
            else if (x > target.getX()){
                x--;
            }
            if (y < target.getY()){
                y++;
            }
            else if (y > target.getY()){
                y--;
            }
        }
    }

    @Override
    public void nextTurn(){
        setTarget();
        move();
    }

}
