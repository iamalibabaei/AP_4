package models.transportation;
import models.Item;
import models.exceptions.AlreadyAtMaxLevelException;
import models.exceptions.IsWorkingException;

public class Seller extends Transporter
{
    public Seller() {
        super(new int[]{150, 500, 1000}, 5, 20);
        this.capacity = 40;
        this.maxtimeToArriveToFarm = 20;
        this.level = 0;
    }

    @Override
    public void go() throws IsWorkingException
    {
        if (isWorking) {
            throw new IsWorkingException();
        }
        isWorking = true;
        arriveToFarm = maxtimeToArriveToFarm;
    }

    @Override
    public void nextTurn() {
        arriveToFarm -= 1;
        if (arriveToFarm == 0) {
            int money = 0;

            for (Item.Type itemType : list.keySet())
            {
                int elementsell = itemType.SELL_MONEY * list.get(itemType);
                money += elementsell;
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
        this.maxtimeToArriveToFarm = maxtimeToArriveToFarm - UPGRADE_SPEED_BOOST;
        this.capacity = (int)UPGRADE_CAPACITY_INCREACE * (level + 2);
    }

    @Override
    public int getUpgradeCost() throws AlreadyAtMaxLevelException {
        if (level == 3) {
            throw new AlreadyAtMaxLevelException();
        }
        return UPGRADE_COST_LIST[level];

    }

}


