package models.animal;

import models.Entity;
import models.interfaces.Buyable;
import models.map.Cell;
import models.map.Map;

public class Dog extends Animal implements Buyable
{
    private static final int BUY_COST = 2600;

    public Dog(int x, int y, Map map)
    {
        super(x, y, map);
    }

    @Override
    public void setTarget()
    {
        target = null;

        for (Cell[] cells: map.getCells()) {
            for (Cell cell: cells) {
                for (Entity entity : cell.getEntities()) {
                    if (entity instanceof WildAnimal){
                        target = entity;
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void collide(Entity entity)
    {
        if (entity instanceof WildAnimal) {
            entity.die();
            setTarget();
        }
    }

    @Override
    public int getBuyCost()
    {
        return BUY_COST;
    }

    @Override
    public void nextTurn() {
        super.nextTurn();
        if (target == null) setTarget();
    }
}
