package models.animal;

import models.Entity;
import models.map.Map;

public class WildAnimal extends Animal implements Storable
{
    protected Status state;

    private final int SELL_MONEY, OCCUPATION_SPACE;//150.25
    private TypeOfAnimal typeOfAnimal;

    public enum TypeOfAnimal { LION, BEAR }


    public WildAnimal(Map map, TypeOfAnimal typeOfAnimal)
    {
        super(map);
        state = Status.NOT_CAGED;
        this.typeOfAnimal = typeOfAnimal;
        if (typeOfAnimal == TypeOfAnimal.LION) {
            this.SELL_MONEY = 150;
            this.OCCUPATION_SPACE = 25;
        } else {
            this.SELL_MONEY = 100;
            this.OCCUPATION_SPACE = 20;
        }

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
            map.getCell(x, y).getEntities().remove(entity);
        }
    }

    @Override
    public void setTarget()
    {
        this.target = null;
    }

    @Override
    public int getSellMoney()
    {
        return SELL_MONEY;
    }

    @Override
    public int getOccupationSpace()
    {
        return OCCUPATION_SPACE;
    }

    @Override
    public void move() {
        if (this.state != Status.CAGED) {
            super.move();
        }
    }
}
