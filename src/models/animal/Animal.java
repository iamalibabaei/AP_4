package models.animal;

import models.Entity;
import models.map.Map;

import java.util.Random;

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
        if (target == null){
            Random random = new Random();
            this.setX(this.getX() + random.nextInt(3 ) - 1);
            this.setY(this.getY() + random.nextInt(3 ) - 1);
        }
        else {
            if (this.getX() < this.target.getX()){
                this.setX(this.getX() + 1);
            }
            else if (this.getX() > this.target.getX()){
                this.setX(this.getX() - 1);
            }
            if (this.getY() < this.target.getY()){
                this.setY(this.getY() + 1);
            }
            else if (this.getY() > this.target.getY()){
                this.setY(this.getY() - 1);
            }
        }
    }

    public abstract void collide(Entity entity);

    public enum Type
    {
        DOG, CAT, SHEEP, HEN, COW, BEAR, LION;
    }

}
