package models.buildings;

import javafx.scene.image.ImageView;
import javafx.util.Duration;
import models.exceptions.Messages;
import models.Viewable;
import models.interfaces.Upgradable;
import view.utility.SpriteAnimation;
import view.utility.Utility;
import view.utility.constants.PictureAddresses;

import java.util.HashMap;

// todo implement well last upgrade (automatic planting)

public class Well implements Upgradable
{
    public static final int[] UPGRADE_COST = {250, 500}, REFILL_COST = {19, 17, 15};
    public static final int[] CAPACITY = {5, 7, 10}, REFILL_TIME = {4, 4, 3};
    private static final int MAX_LEVEL = 2;
    private static final HashMap<String, ImageView> states;
    private static Well instance = new Well();

    static
    {
        states = new HashMap<>();
        for (int i = 0; i < 3; i++)
        {
            states.put("level" + i, Utility.getImageView(PictureAddresses.WELL_PICTURE_ROOT + i + ".png"));
        }
    }

    private int level;
    private boolean isRefilling;
    private int remainingWater;


    private Well()
    {
        isRefilling = false;
        level = 0;
        remainingWater = CAPACITY[level];
    }

    public static Well getInstance()
    {
        return instance;
    }

    public void extractWater() throws Exception
    {
        if (remainingWater == 0)
            throw new Exception(Messages.WELL_EMPTY);
        remainingWater--;
    }

    public void issueRefill() throws Exception
    {
        if (isRefilling)
            throw new Exception(Messages.WELL_WORKING);
        isRefilling = true;
    }

    @Override
    public void upgrade() throws Exception
    {
        if (level == MAX_LEVEL)
            throw new Exception(Messages.UPGRADE_BEYOND_MAX_LEVEL);
        level++;
    }

    @Override
    public int getUpgradeCost() throws Exception
    {
        if (level == MAX_LEVEL)
            throw new Exception(Messages.ALREADY_AT_MAX_LEVEL);
        return UPGRADE_COST[level];
    }

    @Override
    public int getLevel()
    {
        return level;
    }


    private void refill()
    {
        isRefilling = false;
        remainingWater = CAPACITY[level];
    }

}
