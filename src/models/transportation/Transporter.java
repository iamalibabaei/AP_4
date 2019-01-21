package models.transportation;

import models.objects.Item;
import models.exceptions.IsWorkingException;
import models.exceptions.NotEnoughSpaceException;
import models.interfaces.Time;
import models.interfaces.Upgradable;

import java.util.HashMap;


public abstract class Transporter implements Upgradable, Time {
    protected final int[] UPGRADE_COST_LIST;
    protected final int UPGRADE_SPEED_BOOST;
    protected final int UPGRADE_CAPACITY_INCREASE;
    protected HashMap<Item.Type, Integer> list;

    int capacity, timeToArrive, level, remainTimeToArrive;//timeToArrive = arriving to destination per turn
    protected boolean isWorking = false;

    public boolean isWorking() {
        return isWorking;
    }

    public int getLevel() {
        return level;
    }

    public int getTimeToArrive() {
        return timeToArrive;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getRemainTimeToArrive() {
        return remainTimeToArrive;
    }

    public Transporter(int[] UPGRADE_COST_LIST, int UPGRADE_SPEED_BOOST, int UPGRADE_CAPACITY_INCREASE) {
        this.UPGRADE_COST_LIST = UPGRADE_COST_LIST;
        this.UPGRADE_SPEED_BOOST = UPGRADE_SPEED_BOOST;
        this.UPGRADE_CAPACITY_INCREASE = UPGRADE_CAPACITY_INCREASE;
    }


    public HashMap<Item.Type, Integer> getList() {
        return list;
    }

    public void addToList(Item.Type itemType, int number) throws IsWorkingException, NotEnoughSpaceException {
        // todo double-click feature
        if (isWorking) {
            throw new IsWorkingException();
        }
        if (this.capacity - this.usedCapacity() < itemType.OCCUPIED_SPACE * number) {
            throw new NotEnoughSpaceException();
        }
        if (list.containsKey(itemType)) {
            list.put(itemType, number + list.get(itemType));
        } else {
            list.put(itemType, number);
        }
    }

    private int usedCapacity() {
        int usedCapacity = 0;
        for (Item.Type itemType : list.keySet()) {
            int elementOccupation = itemType.OCCUPIED_SPACE * list.get(itemType);
            usedCapacity += elementOccupation;
        }
        return usedCapacity;
    }

    public abstract void go() throws IsWorkingException;

    @Override
    public abstract void nextTurn();

    public void clearStash() {
        list.clear();
    }
}
