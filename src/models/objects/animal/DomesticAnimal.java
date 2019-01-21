package models.objects.animal;

import models.interfaces.Time;
import models.objects.Entity;
import models.objects.Grass;
import models.objects.Item;
import models.objects.Point;

public class DomesticAnimal extends Animal implements Time
{
    private static final int MAX_SATURATION_RATE = 20;
    private int saturationRate;
    private boolean isHungry;

    public DomesticAnimal(Point point, Type type)
    {
        super(point, type);
        saturationRate = MAX_SATURATION_RATE / 2;
        isHungry = true;
    }

    @Override
    public void collide(Entity entity)
    {
        if (isHungry && entity instanceof Grass)
        {
            ((Grass) entity).eatGrass();
        }
    }

    @Override
    public void nextTurn()
    {
        if (saturationRate == 0)
        {
            this.die();
        }
        saturationRate--;
        if (!isHungry && saturationRate <= MAX_SATURATION_RATE / 2)
        {
            produce();
            isHungry = true;
        }
        setTarget();
        move();
    }

    private void produce()
    {
        map.addToMap(new Item(coordinates, type.PRODUCT));
    }

    @Override
    public void setTarget()
    {
        target = null;

        if (isHungry)
        {
            double dist = 1000;
            for (Grass grass : map.getGrasses())
            {
                double dist1 = coordinates.distanceFrom(grass.getCoordinates());
                if (dist1 < dist)
                {
                    dist = dist1;
                    target = grass.getCoordinates();
                }
            }
        }
    }

}
