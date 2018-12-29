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
            this.x += (random.nextInt(3 ) - 1);
            this.y += (random.nextInt(3 ) - 1);

            if (this.x < 0) { this.x += random.nextInt(2) + 2; }
            if (this.y < 0) { this.y += random.nextInt(2) + 2; }
            if (this.x > map.WIDTH) { this.x -= random.nextInt(2) + 2;}
            if (this.y > map.HEIGHT) { this.y -= random.nextInt(2) + 2;}
        }
        else {
            if (this.x < this.target.getX()){
                this.x++;
            }
            else if (this.x > this.target.getX()){
                this.x--;
            }
            if (this.y < this.target.getY()){
                this.y++;
            }
            else if (this.y > this.target.getY()){
                this.y--;
            }
        }
    }

    public abstract void collide(Entity entity);

    public enum Type
    {
        DOG, CAT, SHEEP, HEN, COW, BEAR, LION;
    }

}
