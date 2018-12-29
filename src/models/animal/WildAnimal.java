package models.animal;

import models.Entity;
import models.map.Map;

public class WildAnimal extends Animal
{
    protected Status state;

    private TypeOfAnimal typeOfAnimal;

    public enum TypeOfAnimal { LION, BEAR }


    public WildAnimal(int x, int y, Map map, TypeOfAnimal typeOfAnimal)
    {
        super(x, y, map);
        state = Status.NOT_CAGED;
        this.typeOfAnimal = typeOfAnimal;
    }

    public enum Status
    {
        NOT_CAGED(0), HARDLY_CAGED(1), AVERAGELY_CAGED(2), ALMOST_CAGED(3), CAGED(4);

        private final int status;

        Status(int status)
        {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }

        public static Status[] STATUS_INDEXED = new Status[] { NOT_CAGED, HARDLY_CAGED, AVERAGELY_CAGED, ALMOST_CAGED, CAGED };
    }

    public Status getState() {
        return state;
    }

    public boolean cage()
    {
        //if the animal is caged returns false
        if (this.state == Status.CAGED) {
            return false;
        }
        this.state = Status.STATUS_INDEXED[state.getStatus() + 1];
        return true;
    }

    @Override
    public void collide(Entity entity)
    {

        if ((this.state != Status.CAGED) && (! (entity instanceof Dog || entity instanceof WildAnimal))) {

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

        if (typeOfAnimal == TypeOfAnimal.LION) {
            return 150;
        } else {
            return  100;


        }
    }

    @Override
    public void move() {
        if (this.state != Status.CAGED) {
            super.move();
        }
    }
}
