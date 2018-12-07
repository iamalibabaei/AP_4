package models.transportation;

import models.Item;
import models.interfaces.Storable;
import models.interfaces.Upgradable;

import java.util.HashMap;

public abstract class Transporter implements Upgradable
{
    int capacity, speed, level, arriveToFarm;//speed = arriving to destination per turn
    protected boolean isWorking = false;
    protected HashMap<Storable, Integer> list;

    public boolean addToList(Storable storable, int number) {
        if (isWorking) {
            return false;
        }
        if (this.capacity - this.usedCapacity() < number * storable.getOccupationSpace() ) {
            return false;
        }
        //TODO inja age 2 ta egg ezafe konim egg ha ba ham afrgh mikonn vli fek nkonm tasiri dashte bashe to ravande kar
        //hala ye chek konim badan
        list.put(storable, number);
        return true;
    }
    
    public int usedCapacity() {
        int usedCapacity = 0;
        for (Storable key : list.keySet())
        {
            int elementOccupation = key.getOccupationSpace();
            elementOccupation = elementOccupation * list.get(key);
            usedCapacity += elementOccupation;
        }
        return usedCapacity;
    }
    
    public abstract void go() throws Exception;

    public abstract void turn();

}
