package models.transportation;

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
            for (Storable key : list.keySet())
            {
                int elementsell = key.getSellMoney();
                elementsell = elementsell * list.get(key);
                money += elementsell;
            }
            list.clear();
            isWorking = false;
            //TODO parsa mayad pule (money) ro be pule kol ezafe koni
        }
    }

    @Override
    public void upgrade()
    {
        this.level ++;
        this.speed--;
        this.capacity =(int) (this.capacity * 1.5);
    }

}
