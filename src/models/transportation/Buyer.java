
package models.transportation;

import models.Map;
import models.exceptions.AlreadyAtMaxLevelException;
import models.exceptions.IsWorkingException;
import models.objects.Item;


public class Buyer extends Transporter
{
    private Map map;
    private static final int BUYER_UPGRADE_SPEED_BOOST = 3,BUYER_UPGRADE_CAPACITY = 20,BASE_CAPACITY = 25,
            BASE_TIME_TO_ARRIVE = 12;
    private final static int[] BUYER_UPGRADE_COST_LIST = {400, 800, 1600};
    public Buyer(Map map) {
        super(BUYER_UPGRADE_COST_LIST, BUYER_UPGRADE_SPEED_BOOST, BUYER_UPGRADE_CAPACITY);
        this.capacity = BASE_CAPACITY;
        this.timeToArrive = BASE_TIME_TO_ARRIVE;
        this.level = 0;
        this.map = map;
    }

    public void go() throws IsWorkingException
    {
        if (isWorking) {
            throw new IsWorkingException();
        }
        isWorking = true;
        remainTimeToArrive = timeToArrive;
    }

    @Override
    public void nextTurn() {
        if (!isWorking) {
            return;
        }
        remainTimeToArrive--;
        if (remainTimeToArrive == 0) {
            for (Item.Type itemType : list.keySet()) {
                for (int i = 0; i < list.get(itemType); i++) {
                    int x = (int)(Math.random() * 30), y = (int)(Math.random() * 30);
                    Item item = new Item(x, y, itemType);
                    map.addToMap(item);
                }
            }
            isWorking = false;
            list.clear();
        }
    }

    @Override
    public void upgrade() throws AlreadyAtMaxLevelException
    {
        if (level == 3) {
            throw new AlreadyAtMaxLevelException();
        }
        this.level ++;
        this.timeToArrive = this.timeToArrive - UPGRADE_SPEED_BOOST;
        this.capacity = capacity + UPGRADE_CAPACITY_INCREASE;
    }

    @Override
    public int getUpgradeCost() throws AlreadyAtMaxLevelException {
        if (level == 3) {
            throw new AlreadyAtMaxLevelException();
        }
        return UPGRADE_COST_LIST[level];
    }
}
