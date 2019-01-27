package models.buildings;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import models.Map;
import models.exceptions.AlreadyAtMaxLevelException;
import models.exceptions.InsufficientResourcesException;
import models.exceptions.IsWorkingException;
import models.interfaces.Time;
import models.interfaces.Upgradable;
import models.objects.Item;
import models.objects.Point;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Workshop implements Upgradable, Time
{
    private static final int[] PRODUCTION_TIME = {15, 14, 13, 11, 8};
    private final Point outputPlace;
    public final java.util.Map<Item.Type, Integer> inputs, outputs;
    public final String name;
    private int level, buildCost;
    private int productionRemainingTime;
    private int productionFactor;

    public int getMaxProductionFactor()
    {
        return maxProductionFactor;
    }

    private int maxProductionFactor;
    private boolean isWorking;
    private Map map;

    public Workshop(String name, Point outputPoint, int buildCost, java.util.Map<Item.Type, Integer> inputs,
                    java.util.Map<Item.Type, Integer> outputs)
    {
        level = 0;
        maxProductionFactor = 1;
        isWorking = false;
        this.name = name;
        outputPlace = outputPoint;
        this.buildCost = buildCost;
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public static Workshop loadJson(String jsonAddress) throws FileNotFoundException
    {
        YaGsonBuilder yaGsonBuilder = new YaGsonBuilder();
        YaGson yaGson = yaGsonBuilder.create();
        FileReader fileReader = new FileReader(jsonAddress);
        Workshop workshop = yaGson.fromJson(new Scanner(fileReader).nextLine(), Workshop.class);
        workshop.getMapAndWarehouse();
        return workshop;
    }

    private void getMapAndWarehouse()
    {
        map = Map.getInstance();
    }

    @Override
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

    public void startWorking(int productionFactor) throws IsWorkingException, InsufficientResourcesException
    {
        if (!isWorking)
        {
            throw new IsWorkingException();
        }
        this.productionFactor = productionFactor;
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
                map.addItem(new Item(outputPlace, itemType));
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