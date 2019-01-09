package models.animal;

import models.Entity;
import models.interfaces.Buyable;
import models.map.Map;

// todo dog upgrade

public class Dog extends Animal implements Buyable
{
    private static final int BUY_COST = 2600;

    public Dog(int x, int y, Animal.Type type, Map map)
    {
        super(x, y, type, map);
    }

    @Override
    public void setTarget()
    {
        target = null;

        for (Cell[] cells: map.getCells()) {
            for (Cell cell: cells) {
                for (Entity entity : cell.getEntities()) {
                    if (entity instanceof WildAnimal){
                        target = entity.getPoint();
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
    }
}
