package models.animal;

import models.Entity;
import models.interfaces.Buyable;
import models.map.Cell;
import models.map.Map;

public class Dog extends Animal implements Buyable
{
    public static final int BUY_COST = 2600, SELL_MONEY = 1300, OCCUPATION_SPACE = 10;

    public Dog(Map map)
    {
        super(map);
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
    public int getSellMoney()
    {
        return SELL_MONEY;
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

    public int getOccupationSpace()
    {
        return OCCUPATION_SPACE;
    }

    @Override
    public int getBuyCost()
    {
        return BUY_COST;
    }

}
