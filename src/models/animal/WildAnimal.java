package models.animal;

import models.Entity;
import models.map.Map;

// todo upgrade cage

public class WildAnimal extends Animal
{
    private State state;

    public WildAnimal(int x, int y, Map map, Animal.Type type)
    {
        super(x, y, type, map);
        state = State.NOT_CAGED;
    }

    public enum State
    {
        NOT_CAGED, HARDLY_CAGED, AVERAGELY_CAGED, ALMOST_CAGED, CAGED;
    }

    public State getState() {
        return state;
    }

    public boolean cage()
    {
        //if the animal is caged returns false
        if (this.state == State.CAGED) {
            return false;
        }

        this.state = State.values()[this.state.ordinal() + 1];
        return true;
    }

    @Override
    public void collide(Entity entity)
    {
        if (state != State.CAGED && !(entity instanceof WildAnimal)) {
            entity.die();
        }
    }

    @Override
    public void move() {
        if (state != State.CAGED) {
            super.move();
        }
    }
}
