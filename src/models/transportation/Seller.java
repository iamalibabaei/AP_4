package models.transportation;
import models.objects.Item;
import models.exceptions.AlreadyAtMaxLevelException;
import models.exceptions.IsWorkingException;

//TODO add box

public class Seller extends Transporter
{
    private static final int[] SELLER_UPGRADE_COST_LIST = {150, 500, 1000};
    private static final int SELLER_UPGRADE_SPEED_BOOST = 5,SELLER_UPGRADE_CAPACITY = 20,BASE_CAPACITY = 40,
            BASE_TIME_TO_ARRIVE = 20;
    public Seller() {
        super(SELLER_UPGRADE_COST_LIST, SELLER_UPGRADE_SPEED_BOOST, SELLER_UPGRADE_CAPACITY);
        this.capacity = BASE_CAPACITY;
        this.timeToArrive = BASE_TIME_TO_ARRIVE;
        this.level = 0;
    }

    @Override
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
        remainTimeToArrive -= 1;
        if (remainTimeToArrive == 0) {
            int money = 0;

            for (Item.Type itemType : list.keySet())
            {
                int elementSell = itemType.SELL_MONEY * list.get(itemType);
                money += elementSell;
            }
            list.clear();
            isWorking = false;
            //TODO pule (money) ro be pule kol ezafe konim
        }
    }

    @Override
    public void upgrade() throws AlreadyAtMaxLevelException
    {
        if (level == 3) {
            throw new AlreadyAtMaxLevelException();
        }
        this.level ++;
        this.timeToArrive = timeToArrive - UPGRADE_SPEED_BOOST;
        this.capacity = UPGRADE_CAPACITY_INCREASE + capacity;
    }

    @Override
    public int getUpgradeCost() throws AlreadyAtMaxLevelException {
        if (level == 3) {
            throw new AlreadyAtMaxLevelException();
        }
        return UPGRADE_COST_LIST[level];
    }

}


