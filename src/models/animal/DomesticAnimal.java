package models.animal;

import models.Entity;
import models.Item;
import models.interfaces.Buyable;
import models.interfaces.Time;
import models.map.Cell;
import models.map.Map;

public class DomesticAnimal extends Animal implements Buyable, Time
{
    private static final int MAX_SATURATION_RATE = 20;
    private int saturationRate;
    private boolean isHungry;
    private Type type;

    public Type getType() {
        return type;
    }

    public DomesticAnimal(int x, int y, Map map, Type type)
    {
        super(x, y, Animal.Type.valueOf(type.toString()), map);
        saturationRate = MAX_SATURATION_RATE / 2;
        isHungry = true;
        this.type = type;
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
        HEN(100, Item.Type.EGG, "hen"),
        SHEEP(1000, Item.Type.FABRIC, "sheep"),
        COW(10000, Item.Type.MILK, "cow");

        public final int BUY_COST;
        public final Item.Type PRODUCT;
        public final String NAME;

        Type(int BUY_COST, Item.Type item, String name)
        {
            this.BUY_COST = BUY_COST;
            this.PRODUCT = item;
            this.NAME = name;
        }

        @Override
        public String toString()
        {
            return NAME.toUpperCase();
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
            if (map.getCell(x, y).getGrass() > 0)
            {
                map.getCell(x, y).eatGrass();
                saturationRate++;
                if (saturationRate == MAX_SATURATION_RATE)
                {
                    isHungry = false;
                }
            }
        }
    }

}