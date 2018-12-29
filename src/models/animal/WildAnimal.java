package models.animal;

import models.Entity;
import models.map.Map;

public class WildAnimal extends Animal
{
    protected State state;

    private Type typeOfAnimal;

    public enum Type { LION, BEAR }


    public WildAnimal(int x, int y, Map map, Type typeOfAnimal)
    {
        super(x, y, map);
        state = State.NOT_CAGED;
        this.typeOfAnimal = typeOfAnimal;
    }

    public enum State
    {
        NOT_CAGED(0), HARDLY_CAGED(1), AVERAGELY_CAGED(2), ALMOST_CAGED(3), CAGED(4);

        private final int status;

        State(int status)
        {
            this.status = status;
        }

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

        this.state = State.values()[this.state.status + 1];
        return true;
    }

    @Override
    public void collide(Entity entity)
    {

        if ((this.state != State.CAGED) && (! (entity instanceof Dog || entity instanceof WildAnimal))) {

            entity.die();
        }
    }

    @Override
    public void setTarget()
    {
        this.target = null;
    }

    public int getSellMoney()
    {

        if (typeOfAnimal == Type.LION) {
            return 150;
        } else {
            return  100;


        }
    }

    @Override
    public void move() {
        if (this.state != State.CAGED) {
            super.move();
        }
    }
}
