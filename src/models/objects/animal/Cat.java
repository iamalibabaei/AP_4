package models.objects.animal;

import models.Map;
import models.buildings.Warehouse;
import models.exceptions.AlreadyAtMaxLevelException;
import models.exceptions.NotEnoughSpaceException;
import models.interfaces.Upgradable;
import models.objects.Entity;
import models.objects.Item;
import models.objects.Point;

import java.util.HashMap;

// todo cat upgrade beyond level 1

public class Cat extends Animal implements Upgradable
{
    private static final int MAX_LEVEL = 1, UPGRADE_COST = 200;
    private static int level;
    private Warehouse warehouse;

    public Cat(Point point, Animal.Type type)
    {
        super(point, type);
        this.warehouse = Warehouse.getInstance();
        level = 0;
    }

    @Override
    public void collide(Entity entity)
    {
        if (entity instanceof Item)
        {
            HashMap<Item.Type, Integer> items = new HashMap<>();
            items.put(((Item) entity).type, 1);
            try
            {
                items = warehouse.store(items);
            } catch (NotEnoughSpaceException e)
            {
                // nothing happens here!
            } finally
            {
                if (items.get(((Item) entity).type) == 0)
                {
                    entity.die();
                }
            }
        }
    }

    @Override
    public void nextTurn()
    {
        super.nextTurn();
    }

    @Override
    public void setTarget()
    {
        target = null;
        if (level == 0 && map.getItems().size() != 0)
        {
            target = map.getItems().get(0).getCoordinates();
        } else
        {
            double dist = 2 * Map.WIDTH;
            for (Item item : map.getItems())
            {
                if (warehouse.getRemainingCapacity() >= item.type.OCCUPIED_SPACE)
                {
                    Point target = item.getCoordinates();
                    double dist1 = coordinates.distanceFrom(target);
                    if (dist1 < dist)
                    {
                        dist = dist1;
                        this.target = target;
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
        level++;
    }

    @Override
    public int getUpgradeCost() throws AlreadyAtMaxLevelException
    {
        if (level == MAX_LEVEL)
            throw new AlreadyAtMaxLevelException();
        return UPGRADE_COST;
    }

}
