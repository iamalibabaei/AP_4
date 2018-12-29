package models.animal;

import models.Entity;
import models.Item;
import models.interfaces.Buyable;
import models.interfaces.Countdown;
import models.map.Cell;
import models.map.Map;

public class DomesticAnimal extends Animal implements Buyable, Countdown
{
    private static int hungerRate = 10;
    private int BUY_COST;
    private static final int MAX_HUNGER_RATE = 20;
    private boolean isProducing, isHungry;

    private Type type;

    public enum Type
    {
        HEN, SHEEP, COW
    }

     public DomesticAnimal(int x, int y, Map map, Type type)
    {
        super(x, y, map);
        if (type == Type.HEN){
            this.BUY_COST = 100;
        }
        else if (type == Type.SHEEP){
            this.BUY_COST = 1000;
        }
        else if (type == Type.COW){
            this.BUY_COST = 10000;
        }
    }

    @Override
    public void countdown() {

        if (isProducing){
            hungerRate--;
            if (hungerRate <= 5){
                produce();
            }
        }
    }

    public Item.Type produce()
    {
        if (type == Type.HEN){ return Item.Type.EGG;}
        else

            if (type == Type.SHEEP){ return Item.Type.FABRIC;}
            else

                return Item.Type.MILK;
    }

    @Override
    public void setTarget()
    {
        target = null;
        if (isProducing || !(isHungry)){
            target = null;
        }
        else {
             outer : for (Cell[] cells : super.map.getCells()){
                for (Cell cell : cells){
                    if (cell.getGrass() > 0){
                        super.target.setX(cell.getX());
                        super.target.setY(cell.getY());
                        break outer;
                    }
                }
            }
        }
    }

    @Override
    public void collide(Entity entity)
    {
        while (isHungry) {
            super.map.getCell(entity.getX(), entity.getY()).eatGrass();
            hungerRate++;
            if (hungerRate >= MAX_HUNGER_RATE){
                isProducing = true;
                isHungry = false;
            }
            if (super.map.getCell(entity.getX(), entity.getY()).getGrass() <= 0){
                setTarget();
            }
        }
        setTarget();
    }

    @Override
    public int getBuyCost()
    {
        return BUY_COST;
    }

}
