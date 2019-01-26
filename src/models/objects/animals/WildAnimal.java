package models.objects.animals;

import models.objects.Entity;
import models.objects.Point;

// todo upgrade cage

public class WildAnimal extends Animal
{
    private WildAnimal.State state;

    public WildAnimal(Point point, Animal.Type type)
    {
        super(point, type);
        state = WildAnimal.State.NOT_CAGED;
    }

    public WildAnimal.State getState()
    {
        return state;
    }

    public void cage()
    {
        if (state != WildAnimal.State.CAGED)
        {
            state = WildAnimal.State.values()[state.ordinal() + 1];
        }
    }

    @Override
    public void collide(Entity entity)
    {
        if (state != WildAnimal.State.CAGED && !(entity instanceof WildAnimal))
        {
            entity.die();
        }
    }

    @Override
    public void move()
    {
        if (state != WildAnimal.State.CAGED)
        {
            super.move();
        }
    }

    public enum State
    {
        NOT_CAGED, HARDLY_CAGED, AVERAGELY_CAGED, ALMOST_CAGED, CAGED
    }

}
