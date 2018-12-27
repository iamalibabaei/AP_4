package models.transportation;

import models.exceptions.MaxlevelException;
import models.Item;
import models.map.Map;

public class Helicopter extends Transporter
{
    private Map map;
    public Helicopter(Map map) {
        this.capacity = 25;
        this.speed = 12;
        this.level = 0;
        this.map = map;
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
        arriveToFarm --;
        if (arriveToFarm == 0) {
            for (Item.Type itemType : list.keySet()) {

                for (int i = 0; i < list.get(itemType); i++) {
                    map.addToMap(Item.Type.TYPE_INDEXED(itemType.getType()));
                }
            }
            isWorking = false;
            list.clear();
        }
    }

    @Override
    public void upgrade() throws MaxlevelException
    {
        this.level ++;
        if (level == 4) {
            throw new MaxlevelException();
        }
        this.speed = this.speed - 3;
        this.capacity =(int) (this.capacity * 1.5);
    }
}
