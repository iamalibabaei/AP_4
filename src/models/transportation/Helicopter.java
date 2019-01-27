package models.transportation;

import models.Map;
import models.objects.Item;
import models.objects.Point;


public class Helicopter extends Transporter
{
    public final String NAME = "helicopter";
    private static final int BUYER_UPGRADE_SPEED_BOOST = 3;
    private static final int BUYER_UPGRADE_CAPACITY = 20;
    private static final int BASE_CAPACITY = 25;
    private static final int BASE_TIME_TO_ARRIVE = 12;
    private static final int[] BUYER_UPGRADE_COST_LIST = {400, 800, 1600};
    private static Helicopter instance = new Helicopter();
    private Map map;

    private Helicopter()
    {
        super(BUYER_UPGRADE_COST_LIST, BUYER_UPGRADE_SPEED_BOOST, BUYER_UPGRADE_CAPACITY, BASE_TIME_TO_ARRIVE,
                BASE_CAPACITY);
        map = Map.getInstance();
    }

    public static Helicopter getInstance()
    {
        return instance;
    }

    @Override
    public int computePrice()
    {
        int price = 0;
        for (java.util.Map.Entry<Item.Type, Integer> entry : list.entrySet())
        {
            price += entry.getValue() * entry.getKey().BUY_COST;
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
                for (java.util.Map.Entry<Item.Type, Integer> entry : list.entrySet())
                {
                    for (int i = 0; i < entry.getValue(); i++)
                    {
                        map.addItem(new Item(Point.randomPoint(Map.WIDTH, Map.HEIGHT), entry.getKey()));
                    }
                }
                isWorking = false;
                clearStash();
            }
        }
    }

}
