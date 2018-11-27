package models.animal;

import models.interfaces.Storable;

public abstract class WildAnimal extends Animal implements Storable
{
    protected Status state;

    public WildAnimal()
    {
        super();
        state = Status.NOT_CAGED;
    }

    enum Status
    {
        NOT_CAGED(0), HARDLY_CAGED(1), AVERAGELY_CAGED(2), ALMOST_CAGED(3), CAGED(4);

        int status;

        Status(int status)
        {
            this.status = status;
        }
    }

    public void cage()
    {

    }

}
