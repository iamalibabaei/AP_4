package models.transportation;
import models.Item;
import models.exceptions.AlreadyAtMaxLevelException;
import models.exceptions.IsWorkingException;

public class Seller extends Transporter
{
    public Seller() {
        this.capacity = 40;
        this.speed = 20;
        this.level = 0;
    }

    @Override
    public void go() throws IsWorkingException
    {
        if (isWorking) {
            throw new IsWorkingException();
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
        this.speed = (4 - level) * 5;
        this.capacity = 20 * (level + 2);
    }

    @Override
    public int getUpgradeCost() throws AlreadyAtMaxLevelException {
        if (level == 3) {
            throw new AlreadyAtMaxLevelException();
        }
        int[] costList = {150, 500, 1000};
        return costList[level];

    }

}


