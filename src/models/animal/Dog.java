package models.animal;

import models.Entity;
import models.interfaces.Buyable;
import models.map.Cell;
import models.map.Map;

public class Dog extends Animal implements Buyable
{
    public static final int BUY_COST = 2600;

    public Dog(int x, int y, Map map)
    {
        super(x, y, map);
    }

    @Override
    public void setTarget()
    {
        super.target = null;
        for (Cell[] cells: super.map.getCells()) {
            for (Cell cell: cells) {
                for (Entity entity : cell.getEntities()) {
                    if (entity instanceof WildAnimal){
                        super.target = entity;
                    }
                }
            }
        }
    }

    @Override
    public void collide(Entity entity)
    {
        if (entity instanceof WildAnimal) {
            super.map.getCell(entity.getX(), entity.getY()).getEntities().remove(entity);
            super.map.getCell(entity.getX(), entity.getY()).getEntities().remove(this);
            setTarget();
        }
    }

    @Override
    public int getBuyCost()
    {
        return BUY_COST;
    }

}
