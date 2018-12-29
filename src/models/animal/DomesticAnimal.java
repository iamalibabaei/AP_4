package models.animal;

import models.Entity;
import models.Item;
import models.interfaces.Buyable;
import models.interfaces.Time;
import models.map.Cell;
import models.map.Map;

public class DomesticAnimal extends Animal implements Buyable, Time
{
    public static final int MAX_SATURATION_RATE = 20;
    private int saturationRate = MAX_SATURATION_RATE / 2;
    private boolean isHungry = true;

    public Type getType() {
        return type;
    }

    private Type type;

    public DomesticAnimal(int x, int y, Map map, Type type)
    {
        super(x, y, map);
        this.type = type;
    }

    @Override
    public void nextTurn()
    {
        if (saturationRate <= 0)
        {
            this.die();
        }
        saturationRate--;
        if (!isHungry && saturationRate <= MAX_SATURATION_RATE / 2)
        {
            produce();
            isHungry = true;
        }
        super.nextTurn();
    }

    private void produce()
    {
        map.getCell(x, y).addEntity(new Item(x, y, type.PRODUCT));
    }

    @Override
    public int getBuyCost()
    {
        return type.BUY_COST;
    }

    public enum Type
    {
        HEN(100, Item.Type.EGG),
        SHEEP(1000, Item.Type.FABRIC),
        COW(10000, Item.Type.MILK);

        public final int BUY_COST;
        public final Item.Type PRODUCT;

        Type(int BUY_COST, Item.Type item)
        {
            this.BUY_COST = BUY_COST;
            this.PRODUCT = item;
        }
    }

    @Override
    public void setTarget()
    {
        target = null;
        if (isHungry)
        {
            int dist = 1000;
            for (Cell[] cells : map.getCells())
            {
                for (Cell cell : cells)
                {
                    if (cell.getGrass() > 0)
                    {
                        int dist1 = Math.abs(this.x - cell.getX()) + Math.abs(this.y - cell.getY());
                        if (dist1 < dist)
                        {
                            dist = dist1;
                            target.setX(cell.getX());
                            target.setY(cell.getY());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void collide(Entity entity)
    {
        if (isHungry)
        {
            if (map.getCell(entity.getX(), entity.getY()).getGrass() > 0)
            {
                map.getCell(entity.getX(), entity.getY()).eatGrass();
                saturationRate++;
                if (saturationRate >= MAX_SATURATION_RATE)
                {
                    isHungry = false;
                }
            }
        }
        setTarget();
    }


}