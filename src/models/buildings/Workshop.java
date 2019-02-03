package models.buildings;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import models.Map;
import models.exceptions.Messages;
import models.Viewable;
import models.interfaces.Time;
import models.interfaces.Upgradable;
import models.objects.Item;
import models.objects.Point;
import view.utility.constants.PictureAddresses;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Workshop extends Viewable implements Upgradable, Time
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

    @Override
    protected void loadAnimation()
    {

    }

    @Override
    public String getName()
    {
        return name;
    }

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

    public void startWorking(int productionFactor) throws IOException
    {
        if (!isWorking)
        {
            throw new IOException(Messages.WORKSHOP_WORKING);
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
    public void upgrade() throws IOException
    {
        if (level == 4)
        {
            throw new IOException(Messages.UPGRADE_BEYOND_MAX_LEVEL);
        }
        level++;
        maxProductionFactor++;
    }

    @Override
    public int getUpgradeCost() throws IOException
    {
        if (level == 4)
        {
            throw new IOException(Messages.ALREADY_AT_MAX_LEVEL);
        }
        return buildCost + 100 * (1 + level);
    }


    public static ArrayList<String> loadDefaultWorkshops()
    {
        File folder = new File(PictureAddresses.WORKSHOP_ROOT);
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> workshopName = new ArrayList<>();
        if (listOfFiles != null)
        {
            for (File listOfFile : listOfFiles)
            {
                String name = listOfFile.getName();
                name = name.substring(0, name.indexOf("."));
                workshopName.add(name);
            }
        }
        return workshopName;
    }

}