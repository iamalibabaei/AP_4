package models.animal;

import models.Entity;
import models.Map;
import models.interfaces.Buyable;

// todo dog upgrade

public class Dog extends Animal implements Buyable
{
    private static final int BUY_COST = 2600;

    public Dog(double x, double y, Animal.Type type, Map map)
    {
        super(x, y, type, map);
    }

    @Override
    public void setTarget()
    {
        target = null;

        for (Animal animal: map.getAnimals()) {
            if (animal instanceof WildAnimal){
                target.setX(animal.getCoordinates().getX());
                target.setY(animal.getCoordinates().getY());
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
