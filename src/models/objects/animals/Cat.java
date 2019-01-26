package models.objects.animals;

import models.Map;
import models.buildings.Warehouse;
import models.exceptions.AlreadyAtMaxLevelException;
import models.exceptions.NotEnoughSpaceException;
import models.interfaces.Upgradable;
import models.objects.Entity;
import models.objects.Item;
import models.objects.Point;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

// todo cat upgrade beyond level 1
// todo probable bug: collecting more than one item in one turn

public class Cat extends Animal implements Upgradable
{
    private static final int MAX_LEVEL = 1, UPGRADE_COST = 200;
    private static int level = 0;
    private Warehouse warehouse;

    public Cat(Point point, Animal.Type type)
    {
        super(point, type);
        warehouse = Warehouse.getInstance();
    }

    @Override
    public void collide(Entity entity)
    {
        if (entity instanceof Item)
        {
            List<Item> item = new ArrayList<>();
            item.add((Item) entity);
            try
            {
                item = Map.store(item);
            } catch (NotEnoughSpaceException ignore)
            {
                // nothing happens here!
            }
        }
    }

    @Override
    public void setTarget()
    {
        target = null;
        if (level == 0 && !map.getItems().isEmpty())
        {
            target = map.getItems().get(0).getCoordinates();
        } else
        {
            double dist = 2.0 * Map.WIDTH;
            for (Item item : map.getItems())
            {
                if (warehouse.getRemainingCapacity() >= item.type.OCCUPIED_SPACE)
                {
                    Point newTarget = item.getCoordinates();
                    double dist1 = coordinates.distanceFrom(newTarget);
                    if (dist1 < dist)
                    {
                        dist = dist1;
                        target = newTarget;
                    }
                }
            }
        }
    }

    @Override
    public void upgrade() throws AlreadyAtMaxLevelException
    {
        if (level == MAX_LEVEL)
            throw new AlreadyAtMaxLevelException();
        Cat.level++;
    }

    @Override
    public int getUpgradeCost() throws AlreadyAtMaxLevelException
    {
        if (level == MAX_LEVEL)
            throw new AlreadyAtMaxLevelException();
        return UPGRADE_COST;
    }

}
