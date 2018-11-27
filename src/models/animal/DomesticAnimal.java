package models.animal;

import models.Item;
import models.interfaces.Buyable;

public abstract class DomesticAnimal extends Animal implements Buyable
{
    int hungerRate;

    public DomesticAnimal()
    {
        super();
    }

    @Override
    public void move()
    {
        super.move();
    }

    public abstract Item produce();

}
