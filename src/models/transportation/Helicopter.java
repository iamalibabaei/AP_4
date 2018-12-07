package models.transportation;

import models.Entity;
import models.interfaces.Storable;
import models.map.Map;

public class Helicopter extends Transporter
{
    private Map map;
    public Helicopter(Map map) {
        this.capacity = 25;
        this.speed = 12;
        this.level = 1;
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
            for (Storable key : list.keySet()) {
                for (int i = 0; i < list.get(key); i++) {
                    map.addToMap((Entity) key);
                }
            }
            isWorking = false;
            list.clear();
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
