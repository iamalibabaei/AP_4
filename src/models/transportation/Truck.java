package models.transportation;

import models.Item;
import models.interfaces.Storable;

public class Truck extends Transporter
{
    public Truck() {
        this.capacity = 50;
        this.speed = 8;
        this.level = 1;
    }

    @Override
    public void go() throws Exception
    {
        if (isWorking) {
            throw new Exception("truck is not in the farm");
        }
        isWorking = true;
        arriveToFarm = speed;
    }

    @Override
    public void turn() {
        arriveToFarm -= 1;
        if (arriveToFarm == 0) {
            int money = 0;
            for (Item.Type itemType : list.keySet())
            {
                int elementsell = ((Storable) Item.Type.TYPE_INDEXED(itemType.getType())).getSellMoney();
                elementsell = elementsell * list.get(itemType);
                money += elementsell;
            }
            list.clear();
            isWorking = false;
            //TODO pule (money) ro be pule kol ezafe konim
        }
    }

    @Override
    public void upgrade()
    {
        this.level ++;
        this.speed --;
        this.capacity =(int) (this.capacity * 1.5);
    }

}
