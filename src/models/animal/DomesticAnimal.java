package models.animal;

import models.Entity;
import models.Item;
import models.interfaces.Buyable;
import models.interfaces.Countdown;
import models.map.Cell;
import models.map.Map;

public class DomesticAnimal extends Animal implements Buyable, Countdown
{
    public static final int MAX_SATURATED_RATE = 20;
    private int saturatedRate = MAX_SATURATED_RATE / 2;
    private boolean isHungry = true;

    private Type type;

    public enum Type
    {
        HEN(100, Item.Type.EGG),
        SHEEP(1000, Item.Type.FABRIC),
        COW(10000, Item.Type.MILK);

        public final int BUY_COST;
        public final Item.Type PRODUCT;

        Type(int BUY_COST, Item.Type item) {
            this.BUY_COST = BUY_COST;
            this.PRODUCT = item;
        }
    }

     public DomesticAnimal(int x, int y, Map map, Type type)
    {
        super(x, y, map);
        this.type = type;
    }

    @Override
    public void countdown() {

        if (!isHungry){
            saturatedRate--;
            if (saturatedRate <= 5){
                produce();
                isHungry = true;
            }
        }
    }

    public void produce()
    {
        Item item;
        map.getCell(x, y).addEntity(item = new Item(x, y, type.PRODUCT));
    }

    @Override
    public void setTarget()
    {
        if (isHungry){
            for (Cell[] cells : map.getCells()){
                for (Cell cell : cells){
                    if (cell.getGrass() > 0){
                        target.setX(cell.getX());
                        target.setY(cell.getY());
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
            saturatedRate++;
            if (saturatedRate >= MAX_SATURATED_RATE){
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