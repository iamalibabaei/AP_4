package models.buildings;

import com.google.gson.Gson;
import models.Map;
import models.exceptions.AlreadyAtMaxLevelException;
import models.exceptions.InsufficientResourcesException;
import models.exceptions.IsWorkingException;
import models.interfaces.Time;
import models.interfaces.Upgradable;
import models.objects.Item;
import models.objects.Point;

import java.util.HashMap;

public class Workshop implements Upgradable, Time
{
    private static final int[] PRODUCTION_TIME = {15, 14, 13, 11, 8};
    private final Point outputPlace;
    private final HashMap<Item.Type, Integer> inputs, outputs;
    private String name;
    private int level, buildCost;
    private int productionRemainingTime;
    private int productionFactor, maxProductionFactor;
    private Warehouse warehouse;
    private boolean isWorking;
    private Map map;

    public Workshop(String name, Point outputPoint, int buildCost, HashMap<Item.Type, Integer> inputs,
                    HashMap<Item.Type, Integer> outputs)
    {
        this.level = 0;
        this.maxProductionFactor = 1;
        this.isWorking = false;
        this.name = name;
        this.outputPlace = outputPoint;
        this.buildCost = buildCost;
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public static Workshop loadJson(String json)
    {
        Gson gson = new Gson();
        Workshop workshop = gson.fromJson(json, Workshop.class);
        workshop.getMapAndWarehouse();
        return workshop;
    }

    private void getMapAndWarehouse()
    {
        this.map = Map.getInstance();
        this.warehouse = Warehouse.getInstance();
    }

    public int getLevel()
    {
        return level;
    }

    public int getProductionRemainingTime()
    {
        return productionRemainingTime;
    }

    public boolean isWorking()
    {
        return isWorking;
    }

    public String getName()
    {
        return name;
    }

    public void startWorking() throws IsWorkingException, InsufficientResourcesException
    {
        if (!isWorking)
        {
            throw new IsWorkingException();
        }
        productionFactor = warehouse.moveToWorkshop(inputs, maxProductionFactor).get();
        isWorking = true;
        productionRemainingTime = PRODUCTION_TIME[level];
    }

    @Override
    public void nextTurn()
    {
        if (!isWorking)
        {
            return;
        }
        productionRemainingTime--;
        if (productionRemainingTime == 0)
        {
            isWorking = false;
            returnProduct();
        }
    }

    private void returnProduct()
    {
        for (Item.Type itemType : outputs.keySet())
        {
            for (int i = 0; i < productionFactor * outputs.get(itemType); i++)
            {
                //todo hame ye item ha ru ham mioftan x ro ykam jabeja mikonim
                map.addToMap(new Item(outputPlace, itemType));
            }
        }
    }

    @Override
    public void upgrade() throws AlreadyAtMaxLevelException
    {
        if (level == 4)
        {
            throw new AlreadyAtMaxLevelException();
        }
        level++;
        maxProductionFactor++;
    }

    @Override
    public int getUpgradeCost() throws AlreadyAtMaxLevelException
    {
        if (level == 4)
        {
            throw new AlreadyAtMaxLevelException();
        }
        return buildCost + 100 * (1 + level);
    }

}