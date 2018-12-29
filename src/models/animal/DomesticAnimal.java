package models.animal;

import models.Entity;
import models.Item;
import models.interfaces.Buyable;
import models.interfaces.Countdown;
import models.map.Cell;
import models.map.Map;

public class DomesticAnimal extends Animal implements Buyable, Countdown
{
    public static final int MAX_HUNGER_RATE = 20;
    private int hungerRate = MAX_HUNGER_RATE / 2;
    private boolean isProducing = false, isHungry = true;

    private Type type;

    public enum Type
    {
        HEN(100, Item.Type.EGG),
        SHEEP(1000, Item.Type.FABRIC),
        COW(10000, Item.Type.MILK);

        public final int BUY_COST;
        public final Item.Type production;

        Type(int BUY_COST, Item.Type item) {
            this.BUY_COST = BUY_COST;
            this.production = item;
        }
    }

     public DomesticAnimal(int x, int y, Map map, Type type)
    {
        super(x, y, map);
        this.type = type;
    }

    @Override
    public void countdown() {

        if (isProducing){
            hungerRate--;
            if (hungerRate <= 5){
                produce();
                isProducing = false;
                isHungry = true;
            }
        }
    }

    public Item.Type produce()
    {
        return type.production;
    }

    @Override
    public void setTarget()
    {
        if (!(isProducing) || isHungry){
            for (Cell[] cells : super.map.getCells()){
                for (Cell cell : cells){
                    if (cell.getGrass() > 0){
                        super.target.setX(cell.getX());
                        super.target.setY(cell.getY());
                        return;
                    }
                }
            }
        }
        else {
             target = null;

        }
    }

    @Override
    public void collide(Entity entity)
    {
        if (isHungry) {
            map.getCell(entity.getX(), entity.getY()).eatGrass();
            hungerRate++;
            if (hungerRate >= MAX_HUNGER_RATE){
                isProducing = true;
                isHungry = false;
            }
            if (map.getCell(entity.getX(), entity.getY()).getGrass() <= 0){
                setTarget();
            }
        }
        setTarget();
    }

    @Override
    public int getBuyCost()
    {
        return type.BUY_COST;
    }

}