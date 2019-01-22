package models.transportation;

import models.Game;
import models.objects.Item;

//TODO add box

public class Seller extends Transporter
{
    private static final int[] SELLER_UPGRADE_COST_LIST = {150, 500, 1000};
    private static final int SELLER_UPGRADE_SPEED_BOOST = 5, SELLER_UPGRADE_CAPACITY = 20, BASE_CAPACITY = 40,
            BASE_TIME_TO_ARRIVE = 20;
    private Integer money;

    public Seller()
    {
        super(SELLER_UPGRADE_COST_LIST, SELLER_UPGRADE_SPEED_BOOST, SELLER_UPGRADE_CAPACITY, BASE_TIME_TO_ARRIVE,
                BASE_CAPACITY);
        money = Game.getInstance().getMoney();
    }

    @Override
    public void nextTurn()
    {
        if (!isWorking)
        {
            return;
        }
        remainingTimeToArrive--;
        if (remainingTimeToArrive == 0)
        {
            for (Item.Type itemType : list.keySet())
            {
                int elementSell = itemType.SELL_MONEY * list.get(itemType);
                money += elementSell;
            }
            clearStash();
            isWorking = false;
        }
    }

}

