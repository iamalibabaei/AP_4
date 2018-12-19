package models.animal;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import models.Entity;
import models.Item;
import models.interfaces.Buyable;
import models.map.Cell;
import models.map.Map;

public class Cat extends Animal implements Buyable
{
    public static final int BUY_COST = 2500, SELL_MONEY = 0, OCCUPATION_SPACE = 0;
    private static int level;

    public Cat(Map map)
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
                    if (entity instanceof Item){
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
        if (entity instanceof Item){
            super.map.getCell(entity.getX(), entity.getY()).getStorables();
            super.map.getCell(entity.getX(), entity.getY()).getEntities().remove(entity);
        }
    }

    @Override
    public int getBuyCost()
    {
        return BUY_COST;
    }

    @Override
    public int getOccupationSpace()
    {
        return OCCUPATION_SPACE;
    }

    public void upgrade()
    {
        level++;
    }

}
