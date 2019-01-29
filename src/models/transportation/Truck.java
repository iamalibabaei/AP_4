package models.transportation;

import controller.InGameController;
import models.objects.Item;

import java.util.Map;

//TODO add box
// todo add all to truck
// todo double-click feature

public class Truck extends Transporter
{
    private static Truck instance = new Truck();
    private static final int[] SELLER_UPGRADE_COST_LIST = {150, 500, 1000};
    private static final int SELLER_UPGRADE_SPEED_BOOST = 5;
    private static final int SELLER_UPGRADE_CAPACITY = 20;
    private static final int BASE_CAPACITY = 40;
    private static final int BASE_TIME_TO_ARRIVE = 20;

    private Truck()
    {
        super(SELLER_UPGRADE_COST_LIST, SELLER_UPGRADE_SPEED_BOOST, SELLER_UPGRADE_CAPACITY, BASE_TIME_TO_ARRIVE,
                BASE_CAPACITY);
    }

    public static Truck getInstance()
    {
        return instance;
    }

    @Override
    public int computePrice()
    {
        int price = 0;
        for (java.util.Map.Entry<Item.Type, Integer> entry : list.entrySet())
        {
            price += entry.getValue() * entry.getKey().SELL_MONEY;
        }
        return price;
    }

    @Override
    public void nextTurn()
    {
        if (isWorking)
        {
            remainingTimeToArrive--;
            if (remainingTimeToArrive == 0)
            {
                for (Map.Entry<Item.Type, Integer> entry : list.entrySet())
                {
                    int elementSell = entry.getKey().SELL_MONEY * entry.getValue();
                    InGameController.getInstance().moneyDeposit(elementSell);
                }
                clearStash();
                isWorking = false;
            }
        }
    }

    @Override
    public int getLevel() {
        return level;
    }
}

