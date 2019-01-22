package models.transportation;

import models.Map;
import models.objects.Item;
import models.objects.Point;


public class Buyer extends Transporter
{
    private static final int BUYER_UPGRADE_SPEED_BOOST = 3, BUYER_UPGRADE_CAPACITY = 20, BASE_CAPACITY = 25,
            BASE_TIME_TO_ARRIVE = 12;
    private final static int[] BUYER_UPGRADE_COST_LIST = {400, 800, 1600};
    private Map map;

    public Buyer()
    {
        super(BUYER_UPGRADE_COST_LIST, BUYER_UPGRADE_SPEED_BOOST, BUYER_UPGRADE_CAPACITY, BASE_TIME_TO_ARRIVE,
                BASE_CAPACITY);
        map = Map.getInstance();
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
                for (int i = 0; i < list.get(itemType); i++)
                {
                    map.addToMap(new Item(Point.randomPoint(Map.WIDTH, Map.HEIGHT), itemType));
                }
            }
            isWorking = false;
            clearStash();
        }
    }

}
