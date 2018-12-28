package models.transportation;

import models.exceptions.MaxlevelException;
import models.Item;

public class Seller extends Transporter
{
    public Seller() {
        this.capacity = 40;
        this.speed = 20;
        this.level = 0;
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
    public void upgrade() throws MaxlevelException
    {
        this.level ++;
        if (level == 4) {
            throw new MaxlevelException();
        }
        this.speed = (4 - level) * 5;
        this.capacity = 20 * (level + 2);
    }

}


